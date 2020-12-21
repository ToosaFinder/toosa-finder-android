package com.toosafinder.eventCreation

sealed class EventCreationFormState {
    object Valid: EventCreationFormState()
    object EmptyName: EventCreationFormState()
    object EmptyDescription: EventCreationFormState()
    object EmptyLocation: EventCreationFormState()
    object InvalidDate: EventCreationFormState()
    object InvalidTime: EventCreationFormState()
    object DateOverdue: EventCreationFormState()
}
