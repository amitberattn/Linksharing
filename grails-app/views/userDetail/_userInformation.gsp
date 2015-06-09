<div class="tab-container-1">
    <ul>
        <li>
                <article class="entry-item clearfix">
                    <div class="entry-thumb"><a href="#"><img
                            src="${resource(dir: 'images/profile', file: "${session.user.username ?: 'user.png'}")}"
                            alt=""/></a></div>
                    <table>
                        <tr class="entry-content show-text">
                            <th colspan="4" style="text-align: left;"><a href="#">${session.user.firstName} ${session.user.lastName}</a></th>
                        </tr>
                        <tr class="entry-content show-text">
                            <th colspan="4" style="text-align: left;"><a href="#">@${session.user.username}</a></th>
                        </tr>
                        <tr class="entry-content edit-text" style="display: none;">
                            <g:field type="hidden" name="id" value="${session.user.id}"/>
                        </tr>
                        <tr class="entry-content">
                            <th>Subscriptions</th>
                            <th>Post</th>
                        </tr>
                        <tr class="entry-content">
                            <td><a href="href">${my_subscriptions.size()}</a></td>
                            <td><a href="href">${postNo}</a></td>
                        </tr>

                    </table>
                </article>
        </li>
    </ul>
</div><!--tab-container-1-->