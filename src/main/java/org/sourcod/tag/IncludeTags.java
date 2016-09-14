package org.sourcod.tag;

import org.apache.commons.io.input.ReaderInputStream;
import org.apache.commons.lang.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.io.InputStream;

/**
 * IncludeTags
 *
 * @author willeam
 * @time 16-9-5 上午10:20
 */
public class IncludeTags extends TagSupport {

    private static final long serialVersionUID = 1256128348128118980L;

    private String SCId;
    private String SCName;
    private String SCStyle;
    private String SCType;
    private String SCUrl;
    private String SCCharset;
    private String SCRel;

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = this.pageContext.getOut();

        return EVAL_PAGE;
    }

    @Override
    public int doEndTag() throws JspException {
        JspWriter out = null;
        StringBuffer sb = new StringBuffer();
        try {
            out = this.pageContext.getOut();
            if (StringUtils.isEmpty(SCStyle)) {

                /* javasctipy */
                if ("SCJavasctipt".equals(SCType)) {
                    setJavaScript();
                }

                /* css */
                if ("SCCss".equals(SCType)) {
                    setCss();
                }

                /* templete */
                if ("SCTemplete".equals(SCType)) {
                    setTemplete();
                }
            } else {
                sb.append("");
            }
            sb.append("");
            sb.append("");
            sb.append("");
            sb.append("");

            out.print(sb.toString());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.clearBuffer();
                    sb.setLength(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return EVAL_PAGE;
    }

    @Override
    public int doAfterBody() throws JspException {
        return EVAL_PAGE;
    }


    /**
     * 引入javascript文件
     *
     * @return javaSctipt File Tag
     */
    private StringBuffer setJavaScript() {
        StringBuffer sb = new StringBuffer();
        // <script src="javascript" language="javascript" type="text/javascript" async="async" charset="UTF-8" defer="defer" xml:space="default"></script>

        sb.append("<script type=\"text/javascript\"");
        if (StringUtils.isNotEmpty(SCUrl)) {
            sb.append(" src=\"" + SCUrl + "\"");
        }
        if (StringUtils.isNotEmpty(SCCharset)) {
            sb.append(" charset=\"" + SCCharset + "\"");
        }
        sb.append("></script>");
        return sb;
    }

    /**
     * 引入css样式文件
     *
     * @return css File Tag
     */
    private StringBuffer setCss() {
        StringBuffer sb = new StringBuffer();
        // <link href="css" rel="stylesheet" type="text/css" charset="UTF-8" media="all"/>
        sb.append("<link type=\"text/css\"");
        if (StringUtils.isNotEmpty(SCUrl)){
            sb.append(" href=\""+ SCUrl + "\"");
        }
        if (StringUtils.isNotEmpty(SCCharset)){
            sb.append(" charset=\"" + SCCharset + "\"");
        }
        if (StringUtils.isNotEmpty(SCRel)){
            sb.append(" rel=\"" + SCRel + "\"");
        } else {
            sb.append("rel=\"stylesheet\"");
        }
        return sb;
    }

    /**
     * 根据定义模板引入css和javascript文件(或其他文件,例:text/jsx)
     *
     * @return 引入模板定义的所有文件
     */

    private StringBuffer setTemplete() {
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isNotEmpty(SCUrl)){
            InputStream is = new ReaderInputStream();

        }
        return sb;
    }

    public String getSCId() {
        return SCId;
    }

    public void setSCId(String SCId) {
        this.SCId = SCId;
    }

    public String getSCName() {
        return SCName;
    }

    public void setSCName(String SCName) {
        this.SCName = SCName;
    }

    public String getSCStyle() {
        return SCStyle;
    }

    public void setSCStyle(String SCStyle) {
        this.SCStyle = SCStyle;
    }

    public String getSCType() {
        return SCType;
    }

    public void setSCType(String SCType) {
        this.SCType = SCType;
    }

    public String getSCUrl() {
        return SCUrl;
    }

    public void setSCUrl(String SCUrl) {
        this.SCUrl = SCUrl;
    }

    public String getSCCharset() {
        return SCCharset;
    }

    public void setSCCharset(String SCCharset) {
        this.SCCharset = SCCharset;
    }

    public String getSCRel() {
        return SCRel;
    }

    public void setSCRel(String SCRel) {
        this.SCRel = SCRel;
    }
}
