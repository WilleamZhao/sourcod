package org.sourcod.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * IncludeTags
 *
 * @author willeam
 * @time 16-9-5 上午10:20
 */
public class IncludeTags extends TagSupport{

    private String id;
    private String name;
    private String style;

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = this.pageContext.getOut();

        return super.doStartTag();
    }

    @Override
    public int doEndTag() throws JspException {
        return super.doEndTag();
    }

    @Override
    public int doAfterBody() throws JspException {
        return super.doAfterBody();
    }
}
