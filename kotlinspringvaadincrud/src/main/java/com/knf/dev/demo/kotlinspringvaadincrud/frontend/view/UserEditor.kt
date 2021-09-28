package com.knf.dev.demo.kotlinspringvaadincrud.frontend.view

import com.knf.dev.demo.kotlinspringvaadincrud.backend.model.User
import com.knf.dev.demo.kotlinspringvaadincrud.backend.repository.UserRepository
import com.vaadin.flow.component.ClickEvent
import com.vaadin.flow.component.Key
import com.vaadin.flow.component.KeyNotifier
import com.vaadin.flow.component.KeyPressEvent
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import org.springframework.beans.factory.annotation.Autowired

@SpringComponent
@UIScope
class UserEditor @Autowired constructor
    (private val repository: UserRepository) :
    VerticalLayout(), KeyNotifier {
    /* Fields to edit properties in User entity */
    var firstName = TextField("First name")
    var lastName = TextField("Last name")
    var email = TextField("Email")

    /* Action buttons */
    var save = Button("Save", VaadinIcon.CHECK.create())
    var cancel = Button("Cancel")
    var delete = Button("Delete", VaadinIcon.TRASH.create())
    var actions = HorizontalLayout(save, cancel, delete)
    var binder = Binder(
        User::class.java
    )
    private var user: User? = null
    private var changeHandler: ChangeHandler? = null
    fun delete() {
        repository.delete(user)
        changeHandler!!.onChange()
    }

    fun save() {
        repository.save<User>(user!!)
        changeHandler!!.onChange()
    }

    fun editUser(usr: User?) {
        if (usr == null) {
            isVisible = false
            return
        }
        val persisted = usr.id != null
        user = if (persisted) {
            // Find fresh entity for editing
            repository.findById(usr.id).get()
        } else {
            usr
        }
        cancel.isVisible = persisted
        // Bind user properties to similarly named fields
        // Could also use annotation or "manual binding"
        // or programmatically
        // moving values from fields to entities before saving
        binder.bean = user
        isVisible = true

        // Focus first name initially
        firstName.focus()
    }

    fun setChangeHandler(h: ChangeHandler?) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        changeHandler = h
    }

    interface ChangeHandler {
        fun onChange()
    }

    init {
        add(firstName, lastName, email, actions)
        // bind using naming convention
        binder.bindInstanceFields(this)
        // Configure and style components
        isSpacing = true
        save.element.themeList.add("primary")
        delete.element.themeList.add("error")
        addKeyPressListener(Key.ENTER,
            { e: KeyPressEvent? -> save() })
        // wire action buttons to save, delete and reset
        save.addClickListener{ e:
                               ClickEvent<Button?>? -> save() }
        delete.addClickListener{ e:
                                 ClickEvent<Button?>? -> delete() }
        cancel.addClickListener{ e:
                                 ClickEvent<Button?>? -> editUser(user) }
        isVisible = false
    }
}