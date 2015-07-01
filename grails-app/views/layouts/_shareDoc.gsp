<%@ page import="com.linksharing.DocumentResource;com.linksharing.Topic;com.linksharing.UserDetail" %>
<p class="input-block ${hasErrors(bean: documentResourceInstance, field: 'filePath','error')}">
    <label class="required" for="filePath">
        <g:message code="linkShare.filePath.label" default="File" />
        <span>*</span>
    </label>
    <g:field type="file" name="filePath" required="required" class="form-input"/>
</p>

<p class="input-block clearfix ${hasErrors(bean: documentResourceInstance, field: 'description','error')}">
    <label class="required" for="description">
        <g:message code="linkShare.description.label" default="Description" />
        <span>*</span>
    </label>
    <g:textArea name="description" class="form-input" required="required" style="height: 100px !important;"/>
</p>
<p class="input-block clearfix ${hasErrors(bean: documentResourceInstance, field: 'topic','error')}">
    <label class="required" for="topic">
        <g:message code="linkShare.topic.label" default="Topic" />
        <span>*</span>
    </label>
    %{--<g:select id="topic"  name="topic.id" from="${Topic.findAllByCreatedBy(UserDetail.load(session.user?.id),[sort:'name'])}" optionKey="id" optionValue="name" required="required"  class="form-input"></g:select>--}%
    <select name="topic" class="form-input" id="doc-share-modal">
    </select>
</p>
%{--<g:field type="hidden" id="createdBy" name="createdBy.id"  value="${session.user?.id}"/>--}%