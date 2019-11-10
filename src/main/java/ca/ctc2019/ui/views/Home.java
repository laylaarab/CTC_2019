package ca.ctc2019.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ca.ctc2019.ui.MainLayout;
import ca.ctc2019.ui.components.FlexBoxLayout;
import ca.ctc2019.ui.layout.size.Horizontal;
import ca.ctc2019.ui.layout.size.Right;
import ca.ctc2019.ui.layout.size.Uniform;
import ca.ctc2019.ui.util.UIUtils;
import ca.ctc2019.ui.util.css.FlexDirection;
import ca.ctc2019.ui.util.css.FlexWrap;

@PageTitle("Welcome")
@Route(value = "", layout = MainLayout.class)
public class Home extends ViewFrame {

	public Home() {
		setId("home");
		setViewContent(createContent());
	}

	private Component createContent() {
		Html intro = new Html("<h2>Scrap Cycle</h2>");

		Html header = new Html("<h3>Trade, track and target industrial waste material</h3>");
		Html body = new Html("<h5>ScrapCycle connects Canadians to give a new life to an array of existing materials raising financial, ecological and environmental impact.</h5>");
		Image image = new Image("images/background.jpg", "Back");

//		Anchor documentation = new Anchor("https://vaadin.com/docs/business-app/overview.html", UIUtils.createButton("Read the documentation", VaadinIcon.EXTERNAL_LINK));
//		Anchor starter = new Anchor("https://vaadin.com/start/latest/business-app", UIUtils.createButton("Start a new project with Business App", VaadinIcon.EXTERNAL_LINK));
//
//		FlexBoxLayout links = new FlexBoxLayout(documentation, starter);
//		links.setFlexWrap(FlexWrap.WRAP);
//		links.setSpacing(Right.S);

		FlexBoxLayout content = new FlexBoxLayout(intro, header, body, image);
//		content.getStyle().set("background", "url(images/background.jpg)");
		content.setFlexDirection(FlexDirection.COLUMN);
		content.setMargin(Horizontal.AUTO);
		content.setMaxWidth("840px");
		content.setPadding(Uniform.RESPONSIVE_L);
		return content;
	}

}
