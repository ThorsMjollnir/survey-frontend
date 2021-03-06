/*
This file is part of Intake24.

Copyright 2015, 2016, 2017 Newcastle University.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

This file is based on Intake24 v1.0.

© Crown copyright, 2012, 2013, 2014

Licensed under the Open Government Licence 3.0: 

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.ui;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.workcraft.gwt.shared.client.Callback;
import uk.ac.ncl.openlab.intake24.client.CommonMessages;
import uk.ac.ncl.openlab.intake24.client.EmbeddedData;
import uk.ac.ncl.openlab.intake24.client.api.auth.GeneratedCredentials;

public class GenUserForm extends Composite {

    final private static CommonMessages messages = CommonMessages.Util.getInstance();

    final private VerticalPanel form;

    public GenUserForm() {

        FlowPanel container = new FlowPanel();
        container.getElement().addClassName("intake24-login-container");

        FlowPanel header = new FlowPanel();
        header.getElement().addClassName("intake24-login-header");

        FlowPanel formContainer = new FlowPanel();
        formContainer.getElement().addClassName("intake24-login-form-container");

        HTMLPanel welcome = new HTMLPanel("h1", messages.loginForm_welcome());
        header.add(welcome);

        form = new VerticalPanel();
        form.getElement().addClassName("intake24-login-form");

        form.add(new LoadingWidget());

        container.add(header);
        formContainer.add(form);
        container.add(formContainer);

        initWidget(container);
    }

    public void showCredentials(GeneratedCredentials credentials, final Callback onContinueClicked) {
        form.clear();

        FlowPanel userInfo = new FlowPanel();
        userInfo.addStyleName("intake24-user-info-panel");

        userInfo.add(new HTMLPanel(SafeHtmlUtils.fromSafeConstant(messages.genUserWelcome())));
        userInfo.add(new HTMLPanel(SafeHtmlUtils.fromSafeConstant(messages.genUserSaveInfo())));

        String surveyUrl = Window.Location.getProtocol() + "//" + Window.Location.getHost() + Window.Location.getPath();

        Grid userInfoTable = new Grid(2, 2);
        userInfoTable.addStyleName("intake24-user-info-table");

        userInfoTable.setWidget(0, 0, new Label(messages.loginForm_userNameLabel()));
        userInfoTable.setWidget(0, 1, new Label(credentials.userName));
        userInfoTable.setWidget(1, 0, new Label(messages.loginForm_passwordLabel()));
        userInfoTable.setWidget(1, 1, new Label(credentials.password));

        userInfo.add(userInfoTable);

        userInfo.add(new HTMLPanel(SafeHtmlUtils.fromSafeConstant(messages.genUserSurveyLink())));

        FlowPanel urlDiv = new FlowPanel();
        urlDiv.add(new Anchor(surveyUrl, surveyUrl));
        userInfo.add(urlDiv);

        userInfo.add(new HTMLPanel(SafeHtmlUtils.fromSafeConstant(messages.genUserOneSitting())));

        Button cont = WidgetFactory.createGreenButton(messages.genUserContinue(), "genUserFormContinueButton", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                onContinueClicked.call();
            }
        });

        userInfo.add(WidgetFactory.createButtonsPanel(cont));

        form.add(userInfo);
    }

    public void showForbidden() {
        form.clear();

        form.add(new HTMLPanel(SafeHtmlUtils.fromSafeConstant(messages.genUserForbidden(EmbeddedData.surveySupportEmail))));
    }

    public void showServiceError() {
        form.clear();

        form.add(new HTMLPanel(messages.serverErrorTitle()));

        form.add(new HTMLPanel(SafeHtmlUtils.fromSafeConstant(messages.serverErrorText(EmbeddedData.surveySupportEmail))));
    }
}