<#import "parts/common.ftl" as c>

<@c.page>
<a class="btn btn-primary" href="/find" role="button" >
    < Back
</a>

<div class="card-columns">
    <#list languages as language>
    <div class="card my-3">
        <div class="m-2">
            <span>${language.name}</span>
        </div>
        <div class="card-footer text-muted">
            Times used: ${language.numberOfOcurrences}
        </div>
    </div>
    <#else>
    No message
    </#list>
</div>
</@c.page>
