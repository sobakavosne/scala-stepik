package lectures.week2oop

trait Event {
  def id: String
  def evType: String
  def operation: String
}

case class UserEvent(id: String, evType: String, operation: String)
    extends Event
case class SystemEvent(id: String, evType: String, operation: String)
    extends Event

trait Registrable[T <: Event] {
  def update(event: T, status: String): T
}

object EventOps {
  val userEvent = new Registrable[UserEvent] {
    override def update(event: UserEvent, status: String): UserEvent =
      event.copy(operation = status)
  }

  val systemEvent = new Registrable[SystemEvent] {
    override def update(event: SystemEvent, status: String): SystemEvent =
      event.copy(operation = status)
  }
}

class EventOps[T<: Event](event: T)(ops: Registrable[T]) {
  def update(status: String) = ops.update(event, status)
}

object Generics extends App {
  val user1Event = UserEvent("1", "user_event", "account_create")
  val user1Ops = new EventOps(user1Event)(EventOps.userEvent)
  val user1EventUpd = user1Ops.update("permission_add")

  println(s"User1 | Operation: ${user1EventUpd.operation}")

  // case class Unknown(id: String, operation: String)

  // val unknownEvent = new Registrable[Unknown] {
  //   def update(event: Unknown, status: String): Unknown =
  //     event.copy(operation = status)
  // }
}
