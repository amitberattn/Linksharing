<%@ page import="com.linksharing.UserDetail" %>


<p class="input-block ${hasErrors(bean: userDetailInstance, field: 'firstName', 'error')}">
    <label class="required" for="firstName">
        <g:message code="userDetail.firstName.label" default="First Name"/>
        <span>*</span>
    </label>
    <g:textField name="firstName" required="required" class="form-input"/>
</p>

<p class="input-block ${hasErrors(bean: userDetailInstance, field: 'lastName', 'error')}">
    <label class="required" for="lastName">
        <g:message code="userDetail.lastName.label" default="Last Name"/>
        <span>*</span>
    </label>
    <g:textField name="lastName" required="required" class="form-input"/>

</p>

<p class="input-block ${hasErrors(bean: userDetailInstance, field: 'email', 'error')}">
    <label class="required" for="email">
        <g:message code="userDetail.email.label" default="Email"/>
        <span>*</span>
    </label>
    <g:field type="email" name="email" required="required" class="form-input"/>
</p>

<p class="input-block ${hasErrors(bean: userDetailInstance, field: 'username', 'error')}">
    <label class="required" for="username">
        <g:message code="userDetail.username.label" default="User Name"/>
        <span>*</span>
    </label>
    <g:textField name="username" required="required" class="form-input"/>

</p>

<p class="input-block ${hasErrors(bean: userDetailInstance, field: 'password', 'error')}">
    <label class="required" for="password">
        <g:message code="userDetail.password.label" default="Password"/>
        <span>*</span>
    </label>
    <g:field type="password" name="password" required="required" class="form-input"/>
</p>

<p class="input-block ${hasErrors(bean: userDetailInstance, field: 'confirmPassword', 'error')}">
    <label class="required" for="confirmPassword">
        <g:message code="userDetail.password.label" default="Confirm Password"/>
        <span>*</span>
    </label>
    <g:field type="password" name="confirmPassword" required="required" class="form-input"/>
</p>

<p class="input-block ${hasErrors(bean: userDetailInstance, field: 'photo', 'error')}">
    <label class="required" for="photo">
        <g:message code="userDetail.photo.label" default="Photo"/>
        <span>*</span>
    </label>
    <g:field type="file" name="photo" required="required" class="form-input"/>
</p>

