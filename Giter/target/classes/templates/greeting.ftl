<#include "parts/security.ftl">
<#import "parts/common.ftl" as c>

<@c.page>
<#if isUser>
    <h5>Hello, ${name}</h5>
    <div>
        Now you can use this application freely
    </div>
    <div>|</div>
    <a class="btn btn-primary" href="/find" role="button" >
        Click to start a search
    </a>
<#elseif isAdmin>
<h5>Hello, ${name}</h5>
    <div>
        Now you can manage this application freely
    </div>
    <div>|</div>
    <a class="btn btn-primary" href="/find" role="button" >
        Click to start a search
    </a>
<#else>
<h5>Hello, guest</h5>
    <div>
        Register and you would be able to search the information and statistic about all GitHub users and store all the information to use it later
    </div>
    <div>|</div>
    <a class="btn btn-primary" href="/registration" role="button" >
        Click for registration
    </a>
</#if>

</@c.page>
