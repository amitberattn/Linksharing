<%@ page import="com.linksharing.UserDetail" %>


<p class="input-block ">
    <label class="required" for="oldPassword">
        <g:message code="userDetail.password.label" default="Old Password" />
        <span>*</span>
    </label>
    <g:field type="password" name="oldPassword" required="required"  class="form-input"/>

</p>

<p class="input-block ">
    <label class="required" for="password">
        <g:message code="userDetail.password.label" default="New Password" />
        <span>*</span>
    </label>
    <g:field type="password" name="password" required="required"  class="form-input"/>

</p>

<p class="input-block ">
    <label class="required" for="cnfPassword">
        <g:message code="userDetail.password.label" default="Confirm Password" />
        <span>*</span>
    </label>
    <g:field type="password" name="cnfPassword" required="required"  class="form-input"/>

</p>
<p class="contact-button clearfix">
    <g:submitButton name="submit-contact" value="Change Password"/>
</p>