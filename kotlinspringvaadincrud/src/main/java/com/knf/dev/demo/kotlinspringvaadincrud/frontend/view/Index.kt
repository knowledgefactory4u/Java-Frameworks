package com.knf.dev.demo.kotlinspringvaadincrud.frontend.view

import com.knf.dev.demo.kotlinspringvaadincrud.backend.model.User
import com.knf.dev.demo.kotlinspringvaadincrud.backend.repository.UserRepository
import com.knf.dev.demo.kotlinspringvaadincrud.frontend.view.UserEditor.ChangeHandler
import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent
import com.vaadin.flow.component.ClickEvent
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.value.ValueChangeMode
import com.vaadin.flow.router.Route
import org.springframework.util.StringUtils

@Route(value = "/")
class Index(
    private val repo: UserRepository,
    private val editor: UserEditor
) : VerticalLayout() {
    val grid: Grid<User?>
    val filter: TextField
    private val addNewBtn: Button
    fun listUsers(filterText: String?) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repo.findAll())
        } else {
            grid.setItems(
                repo.findByEmailStartsWithIgnoreCase
                    (filterText)
            )
        }
    }

    init {
        grid = Grid(User::class.java)
        filter = TextField()
        addNewBtn = Button("New User", VaadinIcon.PLUS.create())

        // build layout
        val actions = HorizontalLayout(filter, addNewBtn)
        add(actions, grid, editor)
        grid.height = "300px"
        grid.setColumns("id", "firstName", "lastName", "email")
        grid.getColumnByKey("id").setWidth("60px").
        flexGrow = 0
        filter.placeholder = "Filter by email"
        // Hook logic to components

        // Replace listing with filtered content when user changes
        // filter
        filter.valueChangeMode = ValueChangeMode.EAGER
        filter.addValueChangeListener{ e:
              ComponentValueChangeEvent<TextField?, String?> ->
                         listUsers(e.value)
        }

        // Connect selected User to editor or hide if none is selected
        grid.asSingleSelect()
            .addValueChangeListener { e: ComponentValueChangeEvent
            <Grid<User?>?,
                    User?> ->
                editor.editUser(e.value)
            }

        // Instantiate and edit new User the new button is clicked
        addNewBtn.addClickListener { e: ClickEvent<Button?>? ->
            editor.editUser(User("", "", ""))
        }

        // Listen changes made by the editor,
        // refresh data from backend
        editor.setChangeHandler(object : ChangeHandler {
            override fun onChange() {
                editor.isVisible = false
                listUsers(filter.value)
            }
        })

        // Initialize listing
        listUsers(null)
    }
}