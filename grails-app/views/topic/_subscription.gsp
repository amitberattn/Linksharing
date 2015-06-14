<div class="tab-container-1" id="topicItem" topicid="${topicInstance.id}">
    <ul>
        <li>
            <g:form controller="subscription" action="update" class="edit-subscription">
                <article class="entry-item clearfix">
                    <div class="entry-thumb"><a href="#"><img
                            src="${resource(dir: 'images/profile', file: "${topicInstance.createdBy.username ?: 'user.png'}")}"
                            alt=""/></a></div>
                    <table>
                        <tr class="entry-content show-text">
                            <th colspan="4" style="text-align: left;"><g:link controller="topic" action="show"
                                                                              id="${topicInstance.id}">${topicInstance.name}</g:link></th>
                        </tr>
                        <tr class="entry-content edit-text" style="display: none;">
                            <g:field type="hidden" name="id" value="${topicInstance.id}"/>
                            <th colspan="2"><g:textField name="topic.name" value="${topicInstance.name}"/></th>
                            <th><g:submitButton name="editTopic" value="Save" class="form-input-button-blue"/></th>
                            <th><input type="reset" value="Cancel" class="form-input-button-blue"/></th>
                        </tr>
                        <tr class="entry-content">
                            <th colspan="2">@${topicInstance.createdBy.username}</th>
                            <th>Subscriptions</th>
                            <th>Post</th>
                        </tr>
                        <tr class="entry-content">
                            <td colspan="2"><a
                                    href="#">${topicInstance.createdBy.id == session.user?.id ? "" : "Unsubscribe"}</a>
                            </td>
                            <td><a href="href">${topicInstance.subscription.size()}</a></td>
                            <td class="resourceNo" topicid="${topicInstance.id}" style="cursor: pointer">${topicInstance.resource.size()}</td>
                        </tr>

                    </table>
                    <g:select name="seriousness" from="${com.linksharing.Seriousness}" value="Serious"
                              required="required"></g:select>

                    <g:select name="topic.visibility" from="${com.linksharing.Visibility}"
                              value="${topicInstance.visibility}" required="required"></g:select>
                    <div class="edit">
                        <a href="#" class="invite"><asset:image src="placeholders/email-icon.png" class="modal-form"
                                                                alt=""/></a>
                        <a href="#" class="edit-topic"><asset:image src="placeholders/editor.png" alt=""/></a>
                        <a href="#"><asset:image src="placeholders/trash.png" alt=""/></a>
                    </div>
                </article>
            </g:form>
        </li>
    </ul>
</div><!--tab-container-1-->