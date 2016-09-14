package org.sourcod.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * GridTags
 *
 * @author willeam
 * @time 16-08-26 下午3:53
  */
public class GridTags extends TagSupport implements Tag {

    private static final long serialVersionUID = -5894728553956505651L;

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = null;
        out = this.pageContext.getOut();
        StringBuffer sb = new StringBuffer();


        sb.append("");
        try {
            out.println("aaa");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return super.doEndTag();
    }

    @Override
    public int doAfterBody() throws JspException {
        return super.doAfterBody();
    }

    public static void main(String[] args) {
        System.out.println();
    }

}
