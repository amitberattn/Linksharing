<h3 class="widget-title"><span class="title-line"></span><span class="title-text">Subscription</span></h3>

<div class="tab-container-1">
    <g:if test="${my_subscriptions.size() > 0}">
        <g:if test="${my_subscriptions.size() > 5}">
            %{--<a href="#">View All</a>--}%
            <p><g:link style="cursor: pointer" controller="subscription" action="show">View All</g:link></p>
        </g:if>
        <ul>

            <g:each in="${my_subscriptions}" status="i" var="subscribe">
                <g:if test="${i<5}">
                <li>
                    <g:form controller="subscription" action="update" class="edit-subscription">
                        <article class="entry-item clearfix">
                            <div class="entry-thumb"><a href="#"><img
                                    src="${resource(dir: 'images/profile', file: "${subscribe.topic.createdBy.username ?: 'user.png'}")}"
                                    alt=""/></a></div>

                            <table>
                                <tr class="entry-content show-text">
                                    <th colspan="4" style="text-align: left;"><g:link controller="topic" action="show"
                                                                                      id="${subscribe.topic.id}">${subscribe.topic.name}</g:link></th>
                                </tr>
                                <tr class="entry-content edit-text" style="display: none;">
                                    <g:field type="hidden" name="id" value="${subscribe.id}"/>
                                    <th colspan="2"><g:textField name="topic.name"
                                                                 value="${subscribe.topic.name}"/></th>
                                    <th><g:submitButton name="editTopic" value="Save"
                                                        class="form-input-button-blue"/></th>
                                    <th><input type="reset" value="Cancel" class="form-input-button-blue"/></th>
                                </tr>
                                <tr class="entry-content">
                                    <th colspan="2">@${subscribe.topic.createdBy.username}</th>
                                    <th>Subscriptions</th>
                                    <th>Post</th>
                                </tr>
                                <tr class="entry-content">
                                    <td colspan="2"><g:link controller="userDetail" action="unsubscribeTopic"
                                                            id="${subscribe.topic.id}">${subscribe.topic.createdBy.id == session.user?.id ? "" : "Unsubscribe"}</g:link>
                                    </td>
                                    <td><a href="href">${subscribe.topic.subscription.size()}</a></td>
                                    <td><a href="href">${subscribe.topic.resource.size()}</a></td>
                                </tr>

                            </table>
                            <g:select name="seriousness" from="${com.linksharing.Seriousness}"
                                      value="${subscribe.seriousness}" required="required"></g:select>

                            <g:select name="topic.visibility" from="${com.linksharing.Visibility}"
                                      value="${subscribe.topic.visibility}" required="required"></g:select>
                            <div class="edit">
                                <a href="#" class="invite"><asset:image src="placeholders/email-icon.png"
                                                                        class="modal-form" alt=""/></a>
                                <a href="#" class="edit-topic"><asset:image src="placeholders/editor.png" alt=""/></a>
                                <a href="#"><asset:image src="placeholders/trash.png" alt=""/></a>
                            </div>
                        </article>
                    </g:form>
                </li>
                </g:if>
            </g:each>

        </ul>
    </g:if>
    <g:else>
        <ul>
            <li>
                <article class="entry-item clearfix">
                    <h3 style="color:#e03d3d ;">No subscription found</h3>
                </article>
            </li>
        </ul>
    </g:else>
</div><!--tab-container-1-->