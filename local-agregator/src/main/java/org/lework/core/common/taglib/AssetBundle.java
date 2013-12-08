package org.lework.core.common.taglib;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.lework.core.common.AppConfigConstant;
import org.lework.runner.utils.Collections3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 自定义jsp2.0标签,绑定标签
 * 绑定同类型子标签（javascript，stylesheet）到单独文件
 * <p>
 * 属性说明：
 * name
 * </p>
 * User:Gongle
 * Date: 2013-10-01 18:51:48
 */
public class AssetBundle extends BodyTagSupport {
    private static Logger logger = LoggerFactory.getLogger(AssetBundle.class);
    public static final String DEFAULT_PATH_SYMBOL = "/";
    //使用GuavaCache,最大缓存个数:100,过期时间:3小时
    public static final LoadingCache<String, String> CACHE;

    static {
        CACHE = CacheBuilder.newBuilder().maximumSize(100)
                .expireAfterAccess(3, TimeUnit.HOURS).build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        return key;
                    }
                });
    }

    private String name;
    /**
     * 合并文件的个数*
     */
    private Integer size = 10;
    private List<AssetJavascript> scripts = new ArrayList<AssetJavascript>();
    private List<AssetStylesheet> stylesheets = new ArrayList<AssetStylesheet>();


    @Override
    public int doAfterBody() throws JspException {
        BodyContent bc = getBodyContent();
        final JspWriter out = bc.getEnclosingWriter();
        try {
            //存在缓存则直接返回

            String jsfileNames = Collections3.extractToString(scripts, "src", ";");
            String cssfileNames = Collections3.extractToString(stylesheets, "src", ";");
            String cssMinUIR = AppConfigConstant.CTX + "/static";
            String jsMinPath = AppConfigConstant.CTX + "/static";
            if (CACHE.get(cssfileNames) == null) {
                //处理stylesheets tag
                InputStream in;
                try {
                    in =BundleUtils.mergeResourceAsOne(stylesheets);
                    cssMinUIR += BundleUtils.compressorAndWrite(in, String.format("/%s.min.css", BundleUtils.buildSha1(cssfileNames)));
                    out.print(String.format("<link href=\"%s\" type=\"text/css\" rel=\"stylesheet\" media=\"all\" />\"", cssMinUIR));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                CACHE.put(cssfileNames, cssfileNames);
            }


        } catch (IOException e) {
            throw new JspException("Error:" + e.getMessage());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

    @Override
    public int doStartTag() throws JspException {
        return super.doStartTag();
    }


    //添加一个Script标签
    public void addScript(AssetJavascript script) {
        scripts.add(script);
    }

    //添加一个StyleSheet标签
    public void addStylesheet(AssetStylesheet stylesheet) {
        stylesheets.add(stylesheet);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
