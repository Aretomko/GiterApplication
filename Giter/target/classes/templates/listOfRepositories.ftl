<#import "parts/common.ftl" as c>

<@c.page>
<a class="btn btn-primary" href="/find" role="button">
    < Back
</a>
<div class="card-columns">
    <#list repos as repo>
    <div class="card my-3">
        <div class="m-2">
            <span>${repo.name}</span>
        </div>
        <div class="card-footer text-muted">
        <#if repo.language??>
            ${repo.language}
        </#if>
        </div>
    </div>
    <#else>
    No message
    </#list>
</div>
</@c.page>
