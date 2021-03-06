package uk.ac.ncl.openlab.intake24.client.api.auth;

import com.google.gwt.core.client.GWT;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;
import org.fusesource.restygwt.client.dispatcher.DefaultDispatcher;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Options(serviceRootKey = "intake24-api")
public interface AuthenticationService extends RestService {
    AuthenticationService INSTANCE = GWT.create(AuthenticationService.class);

    @POST
    @Path("/signin/alias")
    void signinWithAlias(Credentials request, MethodCallback<SigninResult> callback);

    @POST
    @Path("/signin/token/{token}")
    void signinWithToken(@PathParam("token") String token, MethodCallback<SigninResult> callback);

    @POST
    @Path("/refresh")
    @Options(dispatcher=RefreshDispatcher.class)
    void refresh(MethodCallback<RefreshResult> callback);
}
