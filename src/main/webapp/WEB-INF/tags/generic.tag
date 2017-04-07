<%@ tag import="uk.ac.ebi.biostudies.auth.Session" %>
<%@ tag description="Generic page" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@attribute name="postBody" fragment="true" %>
<%@attribute name="head" fragment="true" %>
<%@attribute name="breadcrumbs" fragment="true" %>
<%@attribute name="preContent" fragment="true" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="currentUser" value="${Session.getCurrentUser()}"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>The European Bioinformatics Institute &lt; EMBL-EBI</title>
    <meta name="description" content="EMBL-EBI"><!-- Describe what this page is about -->
    <meta name="keywords" content="bioinformatics, europe, institute"><!-- A few keywords that relate to the content of THIS PAGE (not the whol project) -->
    <meta name="author" content="EMBL-EBI"><!-- Your [project-name] here -->
    <meta name="HandheldFriendly" content="true" />
    <meta name="MobileOptimized" content="width" />
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta name="theme-color" content="#70BDBD"> <!-- Android Chrome mobile browser tab color -->

    <!-- Add information on the life cycle of this page -->
    <meta name="ebi:owner" content="John Doe"> <!-- Who should be contacted about changes -->
    <meta name="ebi:review-cycle" content="30"> <!-- In days, how often should the content be reviewed -->
    <meta name="ebi:last-review" content="2015-12-20"> <!-- The last time the content was reviewed -->
    <meta name="ebi:expiry" content="2016-01-20"> <!-- When this content is no longer relevant -->

    <!-- If you link to any other sites frequently, consider optimising performance with a DNS prefetch -->
    <link rel="dns-prefetch" href="//embl.de" />

    <!-- If you have custom icon, replace these as appropriate.
         You can generate them at realfavicongenerator.net -->
    <link rel="icon" type="image/x-icon" href="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/images/logos/EMBL-EBI/favicons/favicon.ico" />
    <link rel="icon" type="image/png" href="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/images/logos/EMBL-EBI/favicons/favicon-32x32.png" />
    <link rel="icon" type="image/png" sizes="192×192" href="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/images/logos/EMBL-EBI/favicons/android-chrome-192x192.png" /> <!-- Android (192px) -->
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/images/logos/EMBL-EBI/favicons/apple-icon-114x114.png"> <!-- For iPhone 4 Retina display (114px) -->
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/images/logos/EMBL-EBI/favicons/apple-icon-72x72.png"> <!-- For iPad (72px) -->
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/images/logos/EMBL-EBI/favicons/apple-icon-144x144.png"> <!-- For iPad retinat (144px) -->
    <link rel="apple-touch-icon-precomposed" href="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/images/logos/EMBL-EBI/favicons/apple-icon-57x57.png"> <!-- For iPhone (57px) -->
    <link rel="mask-icon" href="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/images/logos/EMBL-EBI/favicons/safari-pinned-tab.svg" color="#ffffff"> <!-- Safari icon for pinned tab -->
    <meta name="msapplication-TileColor" content="#2b5797"> <!-- MS Icons -->
    <meta name="msapplication-TileImage" content="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/images/logos/EMBL-EBI/favicons/mstile-144x144.png">

    <!-- CSS: implied media=all -->
    <!-- CSS concatenated and minified via ant build script-->
    <link rel="stylesheet" href="//www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/libraries/foundation-6/css/foundation.css" type="text/css" media="all">
    <link rel="stylesheet" href="//www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/css/ebi-global.css" type="text/css" media="all">
    <link rel="stylesheet" href="//www.ebi.ac.uk/web_guidelines/EBI-Icon-fonts/v1.1/fonts.css" type="text/css" media="all">

    <link rel="stylesheet" href="${contextPath}/css/font-awesome.min.css" type="text/css">

    <!-- Use this CSS file for any custom styling -->
    <!--
      <link rel="stylesheet" href="css/custom.css" type="text/css" media="all">
    -->

    <!-- If you have a custom header image or colour -->
    <!--
    <meta name="ebi:localmasthead-color" content="#000">
    <meta name="ebi:localmasthead-image" content="https://www.ebi.ac.uk/web_guidelines/EBI-Framework/images/backgrounds/embl-ebi-background.jpg">
    -->

    <!-- you can replace this with theme-[projectname].css. See http://www.ebi.ac.uk/web/style/colour for details of how to do this -->
    <!-- also inform ES so we can host your colour palette file -->
    <link rel="stylesheet" href="${contextPath}/css/theme-biostudies.css" type="text/css" media="all">
    <link rel="stylesheet" href="${contextPath}/css/common.css" type="text/css" media="all">

    <!-- for production the above can be replaced with -->
    <!--
    <link rel="stylesheet" href="//www.ebi.ac.uk/web_guidelines/css/compliance/mini/ebi-fluid-embl.css">
    -->

    <!-- end CSS-->


    <!-- All JavaScript at the bottom, except for Modernizr -->
    <script src="//www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/libraries/modernizr/modernizr.custom.49274.js"></script>
    <jsp:invoke fragment="head"/>
</head>
<c:set var="query" value="${param.query}"/>
<body class="level2"><!-- add any of your classes or IDs -->
<div id="skip-to">
    <ul>
        <li><a href="#content">Skip to main content</a></li>
        <li><a href="#local-nav">Skip to local navigation</a></li>
        <li><a href="#global-nav">Skip to EBI global navigation menu</a></li>
        <li><a href="#global-nav-expanded">Skip to expanded EBI global navigation menu (includes all sub-sections)</a></li>
    </ul>
</div>

<div>
    <div id="local-masthead"  data-top-anchor="180" data-btm-anchor="300000">
        <header>

            <div id="global-masthead" class="clearfix">
                <!--This has to be one line and no newline characters-->
                <a href="//www.ebi.ac.uk/" title="Go to the EMBL-EBI homepage"><span class="ebi-logo"></span></a>

                <nav>
                    <div class="row">
                        <ul id="global-nav" class="menu">
                            <!-- set active class as appropriate -->
                            <li id="home-mobile" class=""><a href="//www.ebi.ac.uk"></a></li>
                            <li id="home" class="active"><a href="//www.ebi.ac.uk"><i class="icon icon-generic" data-icon="H"></i> EMBL-EBI</a></li>
                            <li id="services"><a href="//www.ebi.ac.uk/services"><i class="icon icon-generic" data-icon="("></i> Services</a></li>
                            <li id="research"><a href="//www.ebi.ac.uk/research"><i class="icon icon-generic" data-icon=")"></i> Research</a></li>
                            <li id="training"><a href="//www.ebi.ac.uk/training"><i class="icon icon-generic" data-icon="t"></i> Training</a></li>
                            <li id="about"><a href="//www.ebi.ac.uk/about"><i class="icon icon-generic" data-icon="i"></i> About us</a></li>
                            <li id="search">
                                <a href="#" data-toggle="search-global-dropdown"><i class="icon icon-functional" data-icon="1"></i> <span class="show-for-small-only">Search</span></a>
                                <div id="search-global-dropdown" class="dropdown-pane" data-dropdown data-options="closeOnClick:true;">
                                    <form id="global-search" name="global-search" action="/ebisearch/search.ebi" method="GET">
                                        <fieldset>
                                            <div class="input-group">
                                                <input type="text" name="query" id="global-searchbox" class="input-group-field" placeholder="Search all of EMBL-EBI">
                                                <div class="input-group-button">
                                                    <input type="submit" name="submit" value="Search" class="button">
                                                    <input type="hidden" name="db" value="allebi" checked="checked">
                                                    <input type="hidden" name="requestFrom" value="global-masthead" checked="checked">
                                                </div>
                                            </div>
                                        </fieldset>
                                    </form>
                                </div>
                            </li>
                            <li class="float-right show-for-medium embl-selector">
                                <button class="button float-right" type="button" data-toggle="embl-dropdown">Hinxton</button>
                                <!-- The dropdown menu will be programatically added by script.js -->
                            </li>
                        </ul>
                    </div>
                </nav>

            </div>

            <div class="masthead row">

                <!-- local-title -->
                <div class="columns medium-7" id="local-title">
                    <h1><a href="../../" title="Back to BioStudies homepage"><img src="${contextPath}/images/logo.png"/></a></h1>

                </div>
                <!-- /local-title -->
                <div class="column medium-5">
                    <form id="ebi_search" action="${contextPath}/studies">
                        <fieldset>
                            <div class="input-group margin-bottom-none margin-top-large">
                                <input id="query" class="input-group-field" title="EB-eye Search" tabindex="1" type="text" name="query"  size="35" maxlength="2048" placeholder="Search BioStudies" value="${query}" />
                                <div class="input-group-button">
                                    <input id="search_submit" class="button icon icon-functional" tabindex="2" type="submit" value="1" />
                                </div>
                            </div>
                        </fieldset>
                        <p id="example" class="small">
                            Examples: <a class="" href="${contextPath}/studies?query=hyperplasia">hyperplasia</a>, <a class="" href="${contextPath}/studies?query=PMC516016">PMC516016</a>
                            <!--a class="float-right" href="#"><span class="icon icon-generic" data-icon="("></span> advanced search</a-->
                        </p>
                    </form>

                </div>

                <!-- local-nav -->
                <nav >
                    <ul class="dropdown menu float-left" data-description="navigational">
                        <li class=""><a href="${contextPath}/">Home</a></li>
                        <li><a href="${contextPath}/studies/">Browse</a></li>
                        <li><a>Submit</a></li>
                        <li><a>Help</a></li>
                        <li><a>About BioStudies</a></li>
                    </ul>
                    <ul class="dropdown menu float-right" data-description="tasks">
                        <li class=""><a href="${contextPath}"><span class="icon icon-functional" data-icon="n"></span> Feedback</a></li>
                        <li class="">
                            <c:choose>
                                <c:when test="${currentUser!=null}">
                                    <a id="logout-button" href="#"><i class="fa fa-sign-out" aria-hidden="true"></i>
                                        Logout ${currentUser.getUsername()}</a>
                                </c:when>
                                <c:otherwise>
                                    <a id="login-button" href="#"><span class="icon icon-functional" data-icon="l"></span>
                                        Login</a>
                                </c:otherwise>
                            </c:choose>
                        </li>
                    </ul>
                </nav>

                <!-- /local-nav -->

            </div>
        </header>

    </div>
</div>

<jsp:invoke fragment="preContent"/>
<div id="content" role="main" class="row">

    <!-- Suggested layout containers -->
    <section>
        <div id="menu-popup">
            <div id="login-form" class="popup">
                <div class="popup-header">
                    <span class="popup-title">Login</span>
                    <a class="popup-close" href="#"><i class="icon icon-functional" data-icon="x"></i></a>
                    <div class="clearboth"></div>
                </div>
                <form method="post" class="popup-content" action="${contextPath}/auth" >
                    <input  type="hidden" name="t" value="${request.getHeader(HttpTools.REFERER_HEADER)}"/>
                    <fieldset>
                        <input id="user-field" type="text" name="u" maxlength="50" placeholder="Username"/>
                        <input id="pass-field" type="password" name="p" maxlength="50" placeholder="Password"/>
                    </fieldset>
                    <fieldset>
                        <input id="login-remember" name="r" type="checkbox"/>
                        <label for="login-remember">Remember me</label>
                        <input class="submit button" type="submit" value="Login"/>
                    </fieldset>
                    <div id="login-status" class="alert" style="display:none"></div>
                </form>
                <form id="logout-form" method="post" class="popup-content" action="${contextPath}/logout" >
                </form>
            </div>
        </div>
        <!-- Your menu structure should make a breadcrumb redundant, but if a breadcrumb is needed uncomment the below -->
        <nav aria-label="You are here:" role="navigation">
            <jsp:invoke fragment="breadcrumbs"/>
        </nav>

        <div id="main-content-area">
            <section id='renderedContent'>
                <jsp:doBody/>
            </section>
        </div>

    </section>
    <!-- End suggested layout containers -->

</div>


<footer>
    <!-- Optional local footer (insert citation / project-specific copyright / etc here -->
    <!--
          <div id="local-footer">
            <div class="row">
              <span class="reference">How to reference this page: ...</span>
            </div>
          </div>
     -->
    <!-- End optional local footer -->

    <div id="global-footer">

        <nav id="global-nav-expanded" class="row">
            <!-- Footer will be automatically inserted by footer.js -->
        </nav>

        <section id="ebi-footer-meta" class="row">
            <!-- Footer meta will be automatically inserted by footer.js -->
        </section>

    </div>

</footer>
</div> <!--! end of #wrapper -->


<!-- JavaScript -->

<!-- Grab Google CDN's jQuery, with a protocol relative URL; fall back to local if offline -->
<!--
<script>window.jQuery || document.write('<script src="../js/libs/jquery-1.10.2.min.js"><\/script>')</script>
-->
<script src="${contextPath}/js/jquery-3.2.0.min.js"></script>
<!-- Your custom JavaScript file scan go here... change names accordingly -->
<!--
<script defer="defer" src="//www.ebi.ac.uk/web_guidelines/js/plugins.js"></script>
<script defer="defer" src="//www.ebi.ac.uk/web_guidelines/js/script.js"></script>
-->
<script defer="defer" src="//www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/js/cookiebanner.js"></script>
<script defer="defer" src="//www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/js/foot.js"></script>
<script defer="defer" src="//www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/js/script.js"></script>

<!-- The Foundation theme JavaScript -->
<script src="//www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/libraries/foundation-6/js/foundation.js"></script>
<script src="//www.ebi.ac.uk/web_guidelines/EBI-Framework/v1.1/js/foundationExtendEBI.js"></script>
<script type="text/JavaScript">$(document).foundation();</script>
<script type="text/JavaScript">$(document).foundationExtendEBI();</script>

<script src='${contextPath}/js/handlebars-v4.0.5.js'></script>
<script src='${contextPath}/js/jquery.cookie.js'></script>
<script src='${contextPath}/js/common.js'></script>
<!-- Google Analytics details... -->
<!-- Change UA-XXXXX-X to be your site's ID -->
<!--
<script>
  window._gaq = [['_setAccount','UAXXXXXXXX1'],['_trackPageview'],['_trackPageLoadTime']];
  Modernizr.load({
    load: ('https:' == location.protocol ? '//ssl' : '//www') + '.google-analytics.com/ga.js'
  });
</script>
-->
<script>
    var contextPath = '${contextPath}';
</script>
<jsp:invoke fragment="postBody"/>
</body>
</html>
