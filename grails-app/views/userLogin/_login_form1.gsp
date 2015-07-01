<p>
    <label for='username' class="required"><g:message code="springSecurity.login.username.label"/><span>*</span>:</label>
    <g:textField name="j_username" id="username" class="form-input"></g:textField>
</p>

<p>
    <label for='password' class="required"><g:message code="springSecurity.login.password.label"/><span>*</span>:</label>
    <g:field type="password" class="text_ form-input" name="j_password" id="password"></g:field>
</p>

<p id="remember_me_holder">
    <input type='checkbox' class='chk' name='${rememberMeParameter}' id='remember_me' <g:if test='${hasCookie}'>checked='checked'</g:if>/>
    <label for='remember_me'><g:message code="springSecurity.login.remember.me.label"/></label>
</p>
<g:link controller="userLogin" action="forgotPassword" style="cursor: pointer">Forgot password?</g:link>
<p>

    <p>


<div class="fb-login-button" scope="email,public_profile" onlogin="checkLoginState();">
    Login with Facebook
</div>

</p>
    <input type='submit' id="submit" value='${message(code: "springSecurity.login.button")}'/>
</p>