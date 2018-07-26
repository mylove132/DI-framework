package cn.okjiaoyu;

import cn.okjiaoyu.entity.Data;
import cn.okjiaoyu.entity.Handle;
import cn.okjiaoyu.entity.Param;
import cn.okjiaoyu.entity.View;
import cn.okjiaoyu.helper.*;
import cn.okjiaoyu.util.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: liuzhanhui
 * @Decription:请求转发器
 * @Date: Created in 2018-07-24:17:23
 * Modify date: 2018-07-24:17:23
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        //初始化相关的helper类
        HelperLoader.init();
        //获取上下文(用于注册servlet)
        ServletContext servletContext = servletConfig.getServletContext();
        //注册处理jsp的servlet
        ServletRegistration jspRegistration = servletContext.getServletRegistration("jsp");
        jspRegistration.addMapping(ConfigHelper.getAppJspPath() + "*");
        //注册处理静态的servlet
        ServletRegistration assetRegistration = servletContext.getServletRegistration("default");
        assetRegistration.addMapping(ConfigHelper.getAppAssetPath()+"*");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestMethod = request.getMethod().toLowerCase();
        String requestPath = request.getPathInfo();
        //获取handle
        Handle handle = ControllerHelper.getHandle(requestMethod, requestPath);
        if (handle != null){
            //获取controller类
            Class<?> controllerClass = handle.getControllerClass();
            //获取controller实例
            Object controllerInstance = BeanHelper.getBean(controllerClass);
            //定义请求的map
            Map<String, Object> paramMap = new HashMap<String, Object>();
            //获取所有请求参数的名字
            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()){
                String paramName = paramNames.nextElement();
                String paramValue = request.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }
            String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
            if (StringUtil.isNotEmpty(body)){
                String[] params = StringUtils.split(body, "&");
                if (ArrayUtils.isNotEmpty(params)){
                    for (String param : params){
                        String[] array = StringUtils.split(param, "=");
                        if (ArrayUtils.isNotEmpty(array) && array.length == 2){
                            paramMap.put(array[0], array[1]);
                        }
                    }
                }
            }
            Param param = new Param(paramMap);
            //获取action方法，执行action方法
            Method actionMethod = handle.getActionMethod();
            Object result = ReflectionUtil.invokeMethod(controllerInstance, actionMethod, param);
            if (result instanceof View){
                //返回jsp页面
                View view = (View) result;
                String path = view.getPath();
                if (StringUtil.isNotEmpty(path)){
                    if (path.startsWith("/")){
                        response.sendRedirect(request.getContextPath()+path);
                    }else{
                       Map<String, Object> model = view.getModel();
                       for (Map.Entry<String, Object> modelmap : model.entrySet()){
                           request.setAttribute(modelmap.getKey(), modelmap.getValue());
                           request.getRequestDispatcher(ConfigHelper.getAppJspPath()+path).forward(request, response);
                       }
                    }
                }
            }else if (result instanceof Data){
                Data data = (Data) result;
                Object model = data.getModel();
                if (model != null){
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter printWriter = response.getWriter();
                    String json = JsonUtil.toJson(model);
                    printWriter.write(json);
                    printWriter.flush();
                    printWriter.close();
                }
            }
        }
    }

}
