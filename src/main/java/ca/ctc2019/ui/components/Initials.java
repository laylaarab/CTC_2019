package ca.ctc2019.ui.components;

import com.vaadin.flow.component.orderedlayout.FlexComponent;
import ca.ctc2019.ui.util.FontSize;
import ca.ctc2019.ui.util.FontWeight;
import ca.ctc2019.ui.util.LumoStyles;
import ca.ctc2019.ui.util.UIUtils;
import ca.ctc2019.ui.util.css.BorderRadius;

public class Initials extends FlexBoxLayout {

	private String CLASS_NAME = "initials";

	public Initials(String initials) {
		setAlignItems(FlexComponent.Alignment.CENTER);
		setBackgroundColor(LumoStyles.Color.Contrast._10);
		setBorderRadius(BorderRadius.L);
		setClassName(CLASS_NAME);
		UIUtils.setFontSize(FontSize.S, this);
		UIUtils.setFontWeight(FontWeight._600, this);
		setHeight(LumoStyles.Size.M);
		setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
		setWidth(LumoStyles.Size.M);

		add(initials);
	}

}
