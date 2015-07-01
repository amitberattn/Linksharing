<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="master">
    <g:set var="entityName" value="${message(code: 'login.label', default: 'Home Page')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>

<script>
    window.fbAsyncInit = function () {
        FB.init({
            appId: '732451196865250', // App ID
            channelUrl: '//localhost:8080/linksharing', // Channel File
            status: true, // check login status
            cookie: true, // enable cookies to allow the server to access the session
            xfbml: true  // parse XFBML
        });
        // Additional initialization code here
    };
    // Load the SDK Asynchronously
    (function (data) {
        var jse, id = 'facebook-jssdk', ref = data.getElementsByTagName('script')[0];
        if (data.getElementById(id)) {
            return;
        }
        jse = data.createElement('script');
        jse.id = id;
        jse.async = true;
        jse.src = "//connect.facebook.net/en_US/all.js#xfbml=1&appId=732451196865250";
        ref.parentNode.insertBefore(jse, ref);
    }(document));

    function checkLoginState() {
        FB.getLoginStatus(function (response) {
            if (typeof(response) == 'undefined') {
                return
            }
            else {
                var uid = response.authResponse.userID;
                var accessToken = response.authResponse.accessToken;
                getProfileInfoAndLogin(accessToken);
            }

        });

        function getProfileInfoAndLogin(token) {
            var url = "${createLink(controller: 'userLogin', action: 'demoFacebook')}";
            var urlHome = "${createLink(controller: 'userLogin',action: 'auth')}"
            FB.api("/me", "GET", function (response) {
                $.ajax({
                    type: "GET",
                    url: url,
                    dataType: "html",
                    data: {
                        fbEmail: response.email,
                        name: response.name,
                        firstName: response.first_name,
                        lastName: response.last_name,
                        uid: response.id
                    }
                }).done(function (data) {
                    if (data == "true") {
                        window.location = urlHome
                    }
                    else {
                        alert("failure")
                    }
                });
            });
        }
    }
</script>

<!--row-fluid-->

<div id="main-col">

    <div class="widget kopa-article-list-widget">
        <h3 class="widget-title"><span class="title-line"></span><span class="title-text">Trending Now</span></h3>

        <div class="list-container-1">
            <ul class="tabs-1 clearfix">
                <li class="active"><a href="#tab-1-1">Today</a></li>
                <li><a href="#tab-1-2">Last Week</a></li>
                <li><a href="#tab-1-3">Last Month</a></li>
                <li><a href="#tab-1-4">Last Year</a></li>
            </ul><!--tabs-1-->
        </div>

        <div class="tab-container-1">
            <div class="tab-content-1" id="tab-1-1" style="display: block;">
                <g:render template="trendingTopicsByTime" model="${[todyTopicList: todayTopicList]}"></g:render>
            </div><!--tab-content-1-->
            <div class="tab-content-1" id="tab-1-2" style="display: none;">
                <g:render template="trendingTopicsByTime" model="${[todyTopicList: tenDaysTopicList]}"></g:render>
            </div><!--tab-content-1-->
            <div class="tab-content-1" id="tab-1-3" style="display: none;">
                <g:render template="trendingTopicsByTime" model="${[todyTopicList: monthTopicList]}"></g:render>
            </div><!--tab-content-1-->
            <div class="tab-content-1" id="tab-1-4" style="display: none;">
                <g:render template="trendingTopicsByTime" model="${[todyTopicList: topicListYear]}"></g:render>
            </div><!--tab-content-1-->
        </div><!--tab-container-1-->
    </div><!--kopa-article-list-widget-->

</div><!--main-col-->

<div class="widget-area-3 sidebar">

    <div class="widget kopa-article-list-widget">
        <h3 class="widget-title"><span class="title-line"></span><span class="title-text">User Login</span></h3>
        <g:if test="${flash.message}">
            <label class="message" role="status">${flash.message}</label>
        </g:if>
        <g:if test="${flash.error}">
            <label class="error">${flash.error}</label>
        </g:if>
        <g:hasErrors bean="${flash.get("error-msg")}">
            <ul class="error" role="alert">
                <g:eachError bean="${flash.get("error-msg")}" var="error">
                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                            error="${error}"/></li>
                </g:eachError>
            </ul>
        </g:hasErrors>

        <div class="list-container-3">
            <ul class="tabs-3 clearfix">
                <li class="active"><a href="#tab-3-1">Login</a></li>
                <li><a href="#tab-3-2">Registration</a></li>
            </ul><!--tabs-3-->
        </div>

        <div class="tab-container-3">
            <div class="tab-content-3" id="tab-3-1">
                <form action='${postUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off'>
                    <g:render template="login_form1"></g:render>
                </form>

                <div class="clear"></div>
            </div><!--tab-content-3-->
            <div class="tab-content-3" id="tab-3-2">
                <g:uploadForm useToken="true" class="clearfix reg-form" controller="userLogin" action="register"
                              name="contact-form">
                    <g:render template="register_form"/>
                    <p class="contact-button clearfix">
                        <g:submitButton name="submit-contact" value="Register"/>
                    </p>

                    <div class="clear"></div>
                </g:uploadForm>
            </div><!--tab-content-3-->
        </div>

        <div id="response"></div>
    </div><!--contact-box-->
</div><!--widget-area-3-->

<div class="clear"></div>
</body>
</html>
