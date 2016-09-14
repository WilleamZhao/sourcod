package org.sourcod.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * ContainerTags
 *
 * @author willeam
 * @time 16-9-4 下午7:09
 */
public class ContainerTags extends TagSupport {

    private static final long serialVersionUID = -379016874839380620L;

    private String SCName;
    private String SCId;
    private String SCContainer;
    private String SCClass;

    @Override
    public int doStartTag() throws JspException {
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
