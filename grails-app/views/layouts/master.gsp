<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
    <meta charset="utf-8">
    <title>LinkSharing :: <g:layoutTitle default="Home"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="${assetPath(src: 'favicon.ico')}" type="image/x-icon">
    <link rel="apple-touch-icon" href="${assetPath(src: 'apple-touch-icon.png')}">
    <link rel="apple-touch-icon" sizes="114x114" href="${assetPath(src: 'apple-touch-icon-retina.png')}">
    <asset:stylesheet src="css/bootstrap.css"/>
    <asset:stylesheet src="css/theme-options.css"/>
    <asset:stylesheet src="css/style.css"/>
    <asset:stylesheet src="css/bootstrap-responsive.css"/>
    <asset:stylesheet src="css/responsive.css"/>
    <asset:stylesheet src="css/coban.css"/>
    <asset:stylesheet src="css/jquery.dataTables.css"/>

    %{--<link href='http://fonts.googleapis.com/css?family=Rokkitt' rel='stylesheet' type='text/css'>--}%
    %{--<link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>--}%

    <asset:javascript src="js/jquery-1.8.3.min.js"/>
    <asset:javascript src="js/modernizr.custom.js"/>
    <script>
/*        $(document).ready(function(){

            $('#search').keydown(function(e){
                var keyCode = e.which
                if(keyCode == 13){
                    console.log("Enter pressed")
                    console.log(this.value)
                }
            })
        });*/
    </script>

    <g:layoutHead/>

</head>

<body>

<div class="kopa-pattern"></div>


<div class="wrapper kopa-shadow">

    <div class="row-fluid">

        <div class="span12 clearfix">

            <header id="page-header">

                <div id="header-top" class="clearfix">

                    <div class="social-search-box">

                        <div class="sb-search-wrapper">
                            <div id="sb-search" class="sb-search">
                                <g:form controller="search" action="search">
                                    <input class="sb-search-input" placeholder="Enter your search term..." type="text"
                                           value="" name="search" id="search">
                                    <input class="sb-search-submit" type="submit" value="">
                                    <span class="sb-icon-search"></span>-
                                </g:form>
                            </div><!--sb-search-->
                        </div><!--sb-search-wrapper-->

                    </div><!--social-search-box-->

                </div><!--header-top-->

                <div id="header-middle" class="clearfix">

                    <div id="logo-image">
                        <a href="${createLink(uri: '/')}"><asset:image src="placeholders/logo.png" alt=""
                                                                       style="height: 58px"/></a>
                    </div><!--logo-image-->
                    <g:if test="${session.user}">
                        <div class="top-banner">

                            <nav id="main-nav">

                                <ul id="main-menu" class="clearfix">
                                    <li><a href="#" title="Add topic" class="create-topic"><asset:image
                                            src="placeholders/comment.png"
                                            class="modal-form"
                                            alt=""/></a><span></span></li>
                                    <li><g:remoteLink controller="headerMenu" action="invite" update="topic-modal"
                                                      class="invite" title="Send invitation"><asset:image
                                                src="placeholders/inbox.png"
                                                class="modal-form" style=" height:50px;"
                                                alt=""/></g:remoteLink><span></span></li>

                                    <li><g:remoteLink controller="headerMenu" action="invite" update="link-share-modal"
                                                      class="share-link" title="Share Link"><asset:image
                                            src="placeholders/link.png"
                                            class="modal-form"
                                            alt=""/></g:remoteLink><span></span></li>
                                    <li><g:remoteLink controller="headerMenu" action="invite" update="doc-share-modal" class="share-document" title="Add document"><asset:image
                                            src="placeholders/file.png"
                                            class="modal-form"
                                            alt=""/></g:remoteLink><span></span></li>
                                    <li>

                                        %{--<a href="#"><asset:image src="placeholders/user.png" alt=""/></a><span></span>--}%
                                        <a href="#"><img
                                                src="${resource(dir: 'images/profile', file: "${session.user?.username ?: 'user.png'}")}"
                                                alt=""/></a><span></span>
                                        <ul>
                                            <li><g:link controller="userDetail" action="edit">Profile</g:link></li>
                                            %{--<li><a href="gallery.html">Profile</a></li>--}%
                                            <g:if test="${session.user.admin == true}">
                                            <li><g:link controller="userDetail" action="listUser">Users</g:link></li>
                                            </g:if>
                                            <li><a href="elements.html">Topics</a></li>
                                            <li><a href="404.html">Posts</a></li>
                                            <li><g:link controller="login" action="logout">Logout</g:link></li>
                                        </ul>
                                    </li>

                                </ul><!--main-menu-->

                            </nav>

                        </div><!--top-banner-->
                    </g:if>

                </div><!--header-middle-->

            <!-- header-bottom-->

            </header><!--page-header-->
            <div id="main-content">
                <g:if test="${session.user}">
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
                </g:if>
                <g:layoutBody/>
            </div>
            <!--bottom-sidebar-->

            <footer id="page-footer">
                <nav id="footer-nav">
                    <ul id="footer-menu" class="clearfix">
                        <li><a href="http://www.intelligrape.com" target="_blank">LinkSharing.</a></li>
                    </ul><!--footer-menu-->
                </nav><!--footer-nav-->
            </footer><!--page-footer-->

            <p id="back-top">
                <a href="#top">Back to Top</a>
            </p>

        </div><!--span12-->

    </div><!--row-fluid-->

</div><!--wrapper-->
<g:if test="${session.user}">
    <div class="modal-overlay"></div>


    <div class="bg-image">
        <a href="#" class="close"></a>

        <div class="create-topic-body" style="display: none">
            <h3 class="widget-title"><span class="title-text ">Create Topic</span></h3>

            <g:form useToken="true" class="clearfix" controller="topic" action="save">
                <g:render template="/layouts/addTopic"></g:render>

                <p class="contact-button clearfix">
                    <input type="reset" class="form-input form-input-button" value="Cancel">
                    <g:submitButton class="form-input form-input-button" value="Save" name="topicSave"/>
                </p>

                <div class="clear"></div>
            </g:form>
        </div>

        <div class="share-document-body" style="display: none">

            <h3 class="widget-title"><span class="title-text">Share Document</span></h3>

            <g:uploadForm useToken="true" class="clearfix" controller="documentResource" action="save">
                <g:render template="/layouts/shareDoc"></g:render>
                <p class="contact-button clearfix">
                    <input type="reset" class="form-input form-input-button" value="Cancel">
                    <g:submitButton name="shareDoc" class="form-input form-input-button" value="Share"/>
                </p>

                <div class="clear"></div>
            </g:uploadForm>
        </div>

        <div class="share-link-body" style="display: none">

            <h3 class="widget-title"><span class="title-text">Share Link</span></h3>

            <g:form class="clearfix" useToken="true" controller="linkShare" action="save">
                <g:render template="/layouts/shareLink"></g:render>
                <p class="contact-button clearfix">
                    <input type="reset" class="form-input form-input-button" value="Cancel"/>
                    <g:submitButton name="shareLink" type="submit" class="form-input form-input-button" value="Share"/>
                </p>

                <div class="clear"></div>
            </g:form>
        </div>

        <div class="invite-body" style="display: none">

            <h3 class="widget-title"><span class="title-text ">Send Invitation</span></h3>

            <g:form class="clearfix" useToken="true" controller="invitation" action="sendInvitation" method="post">
                <g:render template="/layouts/invite"></g:render>
                <p class="contact-button clearfix">
                    <input type="reset" class="form-input form-input-button" value="Cancel">
                    <input type="submit" class="form-input form-input-button" value="Invite">
                </p>

                <div class="clear"></div>
            </g:form>
        </div>
    </div>
</g:if>
<asset:javascript src="js/superfish.js"/>
<asset:javascript src="js/bootstrap.js"/>
<asset:javascript src="js/query.carouFredSel-6.0.4-packed.js"/>
<asset:javascript src="js/classie.js"/>
<asset:javascript src="js/uisearch.js"/>
<asset:javascript src="js/jquery.validate.min.js"/>
<asset:javascript src="js/custom.js"/>

<script>
    new UISearch(document.getElementById('sb-search'));
</script>

</body>

</html>