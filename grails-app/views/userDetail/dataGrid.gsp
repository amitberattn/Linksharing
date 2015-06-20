<div id="dataTables">

    <table id="countTable">
        <thead>
        <tr>
            <g:sortableColumn property="value"
                              title="${message(code: 'attribute.name.label', default: 'Name')}"/>
            %{--<g:sortableColumn property="attributeGroup"--}%
            %{--title="${'Attribute Group'}"/>--}%
            <g:sortableColumn property="isEnabled"
                              title="${message(code: 'attribute.isEnabled.label', default: 'Is Enabled')}"/>
            %{--<th>Profile Present</th>--}%
        </tr>
        </thead>
        <tbody>
        <g:each in="${attributeInstanceList}" status="i" var="attributeInstance">
            <tr>
                <td>
                    <g:link class="btn-link" action="show" id="${attributeInstance.id}">
                        ${fieldValue(bean: attributeInstance, field: "name")}
                    </g:link>
                </td>
                %{--<td>--}%
                %{--<g:link action="show" id="${attributeInstance.id}">--}%
                %{--${attributeInstance.attributeGroup?.name}--}%
                %{--</g:link>--}%
                %{--</td>--}%
                <td><g:formatBoolean boolean="${attributeInstance.isEnabled}"/></td>
                %{--<td><g:link controller="profile" action="show"--}%
                %{--id="${attributeInstance?.profile?.id}">${attributeInstance?.profile ? "Yes" : "No"}</g:link></td>--}%
            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination right">
        <g:paginate total="${attributeInstanceTotal}"/>
    </ul>
    </div>
</div>
</div>