<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'annonce.label', default: 'Annonce')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
</head>

<body>
<a href="#edit-annonce" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                              default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="edit-annonce" class="content scaffold-edit" role="main">
    <h1><g:message code="default.edit.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${this.annonce}">
        <ul class="errors" role="alert">
            <g:eachError bean="${this.annonce}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                        error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>

    <form action="/annonce/update/${annonce.id}" method="post" enctype="multipart/form-data">

        <fieldset class="form">
            <div class='fieldcontain required'>
                <label for='title'>Title
                    <span class='required-indicator'>*</span>
                </label><input type="text" name="title" value="${annonce.title}" required="" id="title"/>
            </div>

            <div class='fieldcontain required'>
                <label for='description'>Description
                    <span class='required-indicator'>*</span>
                </label><input type="text" name="description" value="${annonce.description}" required=""
                               id="description"/>
            </div>

            <div class='fieldcontain required'>
                <f:field bean="annonce" property="validTill"/>
            </div>

            <div class="fieldcontain">
                <span id="illustrations-label" class="property-label">Illustrations</span>

                <div class="property-value" aria-labelledby="illustrations-label">
                    <input type="file" name="myFile" id="illustration"/>
                </div></div>

            <div class="fieldcontain">

                    <label for="illustration">Illustration</label>
                    <g:each in="${annonce.illustration}" var="illustration">
                    <img src="http://localhost:8080/assets/${illustration.filename}"/>
                    <g:link controller="annonce" action="deleteIllustration"
                            params="[illustrationId: illustration.id, annonceId: annonce.id]">
                        Delete
                    </g:link>
                </g:each>

            </div>

            <div class='fieldcontain'>
                <label for='state'>State</label><input type="hidden" name="_state"/><input type="checkbox" name="state"
                                                                                           checked="checked" id="state"
                                                                                           value="${annonce.state}"/>
            </div>

            <div class='fieldcontain required'>
            </label><f:field bean="annonce" property="author"/>
            </div>
        </fieldset>
        <fieldset class="buttons">
            <input class="update" type="submit"
                   value="${message(code: 'default.button.update.label', default: 'Update')}"/>
        </fieldset>
    </form>
</div>

</body>
</html>
