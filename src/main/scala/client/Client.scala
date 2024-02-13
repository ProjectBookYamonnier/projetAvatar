package client

import machine.MachineImpl
import automaticTester.TestAvatar

object Client extends App {
  TestAvatar.check(MachineImpl)
}
