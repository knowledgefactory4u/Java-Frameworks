package com.knf.dev.demo.springvaadincrud.frontend.view;

import com.knf.dev.demo.springvaadincrud.backend.model.User;
import com.knf.dev.demo.springvaadincrud.backend.repository.UserRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.util.StringUtils;

@Route(value="/")
public class Index extends VerticalLayout {

    final Grid<User> grid;
    final TextField filter;
    private final UserRepository repo;
    private final UserEditor editor;
    private final Button addNewBtn;

    public Index(UserRepository repo, UserEditor editor) {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid<>(User.class);
        this.filter = new TextField();
        this.addNewBtn = new Button
                ("New User", VaadinIcon.PLUS.create());

        // build layout
        HorizontalLayout actions = new
                HorizontalLayout(filter, addNewBtn);
        add(actions, grid, editor);
        grid.setHeight("300px");
        grid.setColumns("id", "firstName", "lastName", "email");
        grid.getColumnByKey("id").setWidth("60px").
                setFlexGrow(0);
        filter.setPlaceholder("Filter by email");
        // Hook logic to components

        // Replace listing with filtered content when user changes filter
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listUsers(e.getValue()));

        // Connect selected User to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editUser(e.getValue());
        });

        // Instantiate and edit new User the new button is clicked
        addNewBtn.addClickListener(e -> editor.editUser
                (new User("", "", "")));

        // Listen changes made by the editor,
        // refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listUsers(filter.getValue());
        });

        // Initialize listing
        listUsers(null);
    }

    void listUsers(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repo.findAll());
        } else {
            grid.setItems(repo.
                    findByEmailStartsWithIgnoreCase(filterText));
        }
    }}