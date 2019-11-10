package ca.ctc2019.ui.views.person;

import ca.ctc2019.backend.Company;
import ca.ctc2019.backend.IndustrialItem;
import ca.ctc2019.ui.MainLayout;
import ca.ctc2019.ui.components.Badge;
import ca.ctc2019.ui.components.FlexBoxLayout;
import ca.ctc2019.ui.util.css.FlexDirection;
import ca.ctc2019.ui.util.css.lumo.BadgeColor;
import ca.ctc2019.ui.views.ViewFrame;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.vaadin.appfoundation.authorization.Resource;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@CssImport("./styles/views/statistics.css")
@PageTitle("Search Items")
@Route(value = "search-items", layout = MainLayout.class)
public class SearchItems extends ViewFrame {

	private Grid<IndustrialItem> gridItems;
	private TextField filterText = new TextField();
	List<IndustrialItem> itemList;

	private static final String CLASS_NAME = "searchitems";
	public static final String MAX_WIDTH = "1024px";

	public SearchItems() {
		setViewContent(createContent());
	}
	private Component listOfItemsView;
	private Component createContent() {
		listOfItemsView = createListOfItemsView();

		filterText = new TextField();
		filterText.setPlaceholder("Filter by item name...");
		filterText.setClearButtonVisible(true);
		filterText.setValueChangeMode(ValueChangeMode.EAGER);
		filterText.addValueChangeListener(e -> updateList());


		FlexBoxLayout content = new FlexBoxLayout(filterText, listOfItemsView);
		content.setAlignItems(FlexComponent.Alignment.CENTER);
		content.setFlexDirection(FlexDirection.COLUMN);
		return content;
	}

	private void updateList() {
		List<IndustrialItem> filteredItemList = itemList.stream().filter(item -> item.getName().contains(filterText.getValue())).collect(Collectors.toList());
		gridItems.setItems(filteredItemList);
	}

	private Dialog getItemView(IndustrialItem item) {
		Dialog dialog = new Dialog();
		Html title = new Html("<h2>" + item.getName() + "</h2>");
		Html desc = new Html("<p>" + item.getDesc() + "</p>");
		Html contact = new Html("<a href=\"mailto:"+ item.getCompany().getEmail() + "\">Contact owner</a>");
//		Image image = new Image(item.get);
		Button close = new Button("Close");
		close.addClickListener(e->{
			dialog.close();
		});
		dialog.add(title, desc, contact, close);
		return dialog;
	}

	private Grid createListOfItemsView() {
		itemList = new LinkedList<>();
		Company company = new Company ("C1", "S1", "City", "AB", "PC", "email@email.ca", 1, "COMPANY","bob","vance");
		itemList.add(new IndustrialItem(IndustrialItem.Type.WOOD, "Item 1", "This is the first item. Wood", company, 122,2, IndustrialItem.Status.AVAILABLE));
		itemList.add(new IndustrialItem(IndustrialItem.Type.METAL, "Item 2", "This is the first item. Metal", company, 12,2, IndustrialItem.Status.SOLD));
		itemList.add(new IndustrialItem(IndustrialItem.Type.PAPER, "Item 3", "This is the first item. Paper", company, 122,200, IndustrialItem.Status.CONDSALE));
		itemList.add(new IndustrialItem(IndustrialItem.Type.OTHER, "Item 4", "This is the first item. Other", company, 13,2010, IndustrialItem.Status.AVAILABLE));

		gridItems = new Grid<>(IndustrialItem.class);
		gridItems.setItems(itemList);

		gridItems.setSelectionMode(Grid.SelectionMode.SINGLE);
		gridItems.addItemClickListener(event ->{
			Dialog dialog = getItemView(event.getItem());
			dialog.open();
		});

		ComponentRenderer<Badge, IndustrialItem> badgeRendererStatus = new ComponentRenderer<>(
				item -> {
					IndustrialItem.Status status = item.getStatus();
					Badge badge;
					switch (status) {
						case SOLD:
							badge = new Badge(status.getName(), BadgeColor.ERROR);
							break;
						case AVAILABLE:
							badge = new Badge(status.getName(), BadgeColor.SUCCESS);
							break;
						case CONDSALE:
							badge = new Badge(status.getName(), BadgeColor.NORMAL);
							break;
						default:
							badge = new Badge(status.getName(), BadgeColor.NORMAL);
					}
					return badge;
				}
		);

		ComponentRenderer<Badge, IndustrialItem> badgeRendererType = new ComponentRenderer<>(
				item -> {
					IndustrialItem.Type type = item.getType();
					return new Badge(type.getName(), BadgeColor.CONTRAST);
				}
		);


		gridItems.removeAllColumns();
		gridItems.addColumn(badgeRendererStatus)
				.setAutoWidth(true)
				.setFlexGrow(1)
				.setHeader("Status");
		gridItems.addColumn(IndustrialItem::getName)
				.setAutoWidth(true)
				.setFlexGrow(1)
				.setHeader("Name");
		gridItems.addColumn(IndustrialItem::getDesc)
				.setAutoWidth(true)
				.setFlexGrow(1)
				.setHeader("Description");
		gridItems.addColumn(badgeRendererType)
				.setAutoWidth(true)
				.setFlexGrow(1)
				.setHeader("Type");
		gridItems.addColumn(IndustrialItem::getPrice)
				.setAutoWidth(true)
				.setFlexGrow(1)
				.setHeader("Price");
		gridItems.addColumn(IndustrialItem::getQuantity)
				.setAutoWidth(true)
				.setFlexGrow(1)
				.setHeader("Quantity");


		return gridItems;
	}

}
