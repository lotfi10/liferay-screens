/**
* Copyright (c) 2000-present Liferay, Inc. All rights reserved.
*
* This library is free software; you can redistribute it and/or modify it under
* the terms of the GNU Lesser General Public License as published by the Free
* Software Foundation; either version 2.1 of the License, or (at your option)
* any later version.
*
* This library is distributed in the hope that it will be useful, but WITHOUT
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
* FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
* details.
*/
import UIKit


class UserPortraitLoadByUserIdInteractor: UserPortraitBaseLoadUserInteractor {

	let userId: Int64

	init(screenlet: BaseScreenlet, userId: Int64) {
		self.userId = userId

		super.init(screenlet: screenlet)
	}

	override func createLoadUserOperation() -> GetUserBaseOperation? {
		return GetUserByUserIdOperation(screenlet: screenlet, userId: userId)
	}

	override func isUserLogged() -> Bool {
		if let userIdValue = SessionContext.userAttribute("userId") as? Int {
			return (userId == Int64(userIdValue))
		}

		return false
	}

}
