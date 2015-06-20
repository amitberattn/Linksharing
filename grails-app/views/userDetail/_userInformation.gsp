<div class="tab-container-1">
    <ul>
        <li>
                <article class="entry-item clearfix">
                    <div class="entry-thumb"><g:link controller="userDetail" action="profile" id="${userDetail.id}"><img
                            src="${resource(dir: 'images/profile', file: "${userDetail.username ?: 'user.png'}")}"
                            alt=""/></g:link></div>
                    <table>
                        <tr class="entry-content show-text">
                            <th colspan="4" style="text-align: left;"><a href="#">${userDetail.firstName} ${userDetail.lastName}</a></th>
                        </tr>
                        <tr class="entry-content show-text">
                            <th colspan="4" style="text-align: left;"><a href="#">@${userDetail.username}</a></th>
                        </tr>
                        <tr class="entry-content edit-text" style="display: none;">
                            <g:field type="hidden" name="id" value="${userDetail.id}"/>
                        </tr>
                        <tr class="entry-content">
                            <th>Subscriptions</th>
                            <th>Post</th>
                        </tr>
                        <tr class="entry-content">
                            <td><g:link controller="subscription" action="show">${my_subscriptions.size()}</g:link></td>
                            <td><a href="href">${postNo}</a></td>
                        </tr>

                    </table>
                </article>
        </li>
    </ul>
</div><!--tab-container-1-->