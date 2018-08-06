<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page language="java"%>
<%@ page import="org.nuxeo.runtime.api.Framework"%>
<%@ page import="org.apache.http.HttpStatus"%>
<%@ page import="org.nuxeo.ecm.tokenauth.service.TokenAuthenticationService"%>
<%
TokenAuthenticationService tokenAuthService = Framework.getService(TokenAuthenticationService.class);
String token = tokenAuthService.acquireToken(request);
if (token == null) {
    response.sendError(HttpStatus.SC_UNAUTHORIZED);
    return;
}
String userName = request.getUserPrincipal().getName();
String updateToken = request.getParameter("updateToken");
Boolean useProtocol = Boolean.parseBoolean(request.getParameter("useProtocol"));
%>
<html>
  <head>
    <title>Nuxeo Drive startup page</title>
    <script type="text/javascript">
      location.replace(location.href + '#token=<%= token %>');
      <% if (useProtocol) { %>
      location.replace('nxdrive://token/<%= token %>');
      <% } else if (updateToken == null) { %>
      drive.create_account('<%= userName %>', '<%= token %>');
      <% } else { %>
      drive.update_token('<%= token %>');
      <% } %>
    </script>
  </head>
  <body>
    <!-- Current user [<%= userName %>] acquired authentication token [<%= token %>] -->
  </body>
</html>
