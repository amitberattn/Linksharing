<div id="fb-root"></div>
<script>
    (function(d, s, id) {
        var js, fjs = d.getElementsByTagName(s)[0];
        if (d.getElementById(id)) return;
        js = d.createElement(s); js.id = id;
        js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.3&appId=732451196865250";
        fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'facebook-jssdk'));</script>
<div class="tab-container-1">
    <ul>
        <li>
            <article class="entry-item clearfix">
                <div class="entry-thumb"><a href="#"><img
                        src="${resource(dir: 'images/profile', file: "${resourceItem.createdBy.username ?: 'user.png'}")}"
                        alt=""/></a></div>
                <div>
                  <table>
                    <tr class="entry-content edit-text" style="display: none;" id="editDiv">
                        <g:field type="hidden" name="id" value="${resourceItem.id}"/>
                        <th colspan="2"><g:textField name="topic.name" id="resDesc" rid="${resourceItem.id}"
                                                     value="${resourceItem.description}"/></th>
                        <th><input type="button" value="Save" id="editButton" class="form-input-button-blue"></th>
                        <th><input type="reset" value="Cancel" class="form-input-button-blue"/></th>
                    </tr>
                      </table>
                </div>

                <div class="entry-content">
                    <p class="entry-description" id="desc">${resourceItem.description}</p>
                    <p class="entry-description">
                    <div class="star" rid="${resourceItem.id}" id="r${resourceItem.id}"></div>
                    </p>
                    <span class="entry-date">
                        <g:if test="${resourceItem instanceof com.linksharing.LinkShare}">
                            <div class="fb-share-button" data-href="${resourceItem.url}" data-layout="button_count"></div>
                            %{--<a href="#"><asset:image src="placeholders/facebook-icon.png" alt=""/></a>--}%
                        </g:if>
                        <g:else>
                            <div class="fb-share-button" data-href="http://localhost:8080/linksharing/images/topic/${resourceItem.fileName}" data-layout="button_count"></div>
                        </g:else>
                        <a href="#"><asset:image src="placeholders/Linkedin.png" alt=""/></a>
                        <a href="#"><asset:image src="placeholders/googleplus.png" alt=""/></a>

                        <div class="modify">
                        %{--<a href="#">View post</a>--}%
                            <g:link controller="resource" action="show" id="${resourceItem.id}"
                                    style="cursor: pointer">View post</g:link>
                            <g:if test="${session.user.id in ((((resourceItem.readingItem as List).findAll {
                                it.isRead == true
                            }).userDetail as List).id as List<Long>)}">
                                <a style="cursor: pointer" id="${resourceItem.id}" class="markread">Mark as unread</a>
                            </g:if>
                            <g:else>
                                <a style="cursor: pointer" id="${resourceItem.id}" class="markread">Mark as read</a>
                            </g:else>
                            <g:if test="${resourceItem instanceof com.linksharing.LinkShare}">
                                <a href="${resourceItem.url}" target="_blank">View full site</a>
                            </g:if>
                            <g:else>
                                %{--<a href="/linksharing/images/topic/${resourceItem.fileName}">Download</a>--}%
                                <g:link style="cursor: pointer" controller="documentResource" action="download" id="${resourceItem.id}">Download</g:link>
                            </g:else>
                            <g:if test="${resourceItem.createdBy.id == session.user.id}">
                                <a href="#" id="edit">Edit</a>
                                <g:link style="cursor: pointer" controller="resource" action="deleteResource" id="${resourceItem.id}">Delete</g:link>
                            </g:if>
                        </div>
                    </span>
                </div>
            </article>
        </li>
    </ul>
</div>