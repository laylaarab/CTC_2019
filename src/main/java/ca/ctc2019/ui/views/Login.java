package ca.ctc2019.ui.views;

import ca.ctc2019.backend.BankAccount;
import ca.ctc2019.backend.LoginController;
import ca.ctc2019.ui.MainLayout;
import ca.ctc2019.ui.components.FlexBoxLayout;
import ca.ctc2019.ui.layout.size.Horizontal;
import ca.ctc2019.ui.layout.size.Top;
import ca.ctc2019.ui.util.css.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;

@PageTitle("Login")
@Route(value = "login", layout = MainLayout.class)
public class Login extends ViewFrame {

    public static final int MOBILE_BREAKPOINT = 480;
    private Registration resizeListener;

    public Login() {
        setViewContent(createContent());
    }

    private Component createContent() {
        FlexBoxLayout content = new FlexBoxLayout(createLogin());

        content.setBoxSizing(BoxSizing.BORDER_BOX);
        content.setHeightFull();
        content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
        return content;
    }

    private Component createLogin() {
		//		if logged in, redirect
		LoginController loginController = LoginController.getInstance();
		if (loginController.isLoggedIn()) {

            Html intro = new Html("<h2>Scrap Cycle</h2>");

//            Html header = new Html("<h3>Trade, track and target industrial waste material</h3>");
//            Html body = new Html("<h5>ScrapCycle connects Canadians to give a new life to an array of existing materials raising financial, ecological and environmental impact.</h5>");
//            Image image = new Image("images/background.jpg", "Back");


            return new FlexBoxLayout(intro);
		} else {
			LoginForm component = new LoginForm();

			component.addLoginListener(e -> {
				boolean isAuthenticated = authenticate(e);
				if (isAuthenticated) {
					Notification.show(
							"Logged in", 3000,
							Notification.Position.TOP_START);
					component.getUI().ifPresent(ui -> ui.getPage().reload());
				} else {
					component.setError(true);
				}
			});
			return component;
		}
    }

    private Boolean authenticate(AbstractLogin.LoginEvent e) {
        LoginController loginController = LoginController.getInstance();
        return loginController.loginUser(e);
    }


//	@Override
//	protected void onDetach(DetachEvent detachEvent) {
//		resizeListener.remove();
//		super.onDetach(detachEvent);
//	}
}
