//
//  AccountSettingsViewController.swift
//  WesterosBank
//
//  Created by jmWork on 26/04/15.
//  Copyright (c) 2015 Liferay. All rights reserved.
//

import UIKit

class AccountSettingsViewController: UIViewController {

	@IBOutlet weak var firstNameField: BorderedTextField!
	@IBOutlet weak var scrollView: UIScrollView!

	override func viewDidLoad() {
		firstNameField.onFocused = onFieldFocused
	}

	override func viewDidAppear(animated: Bool) {
		firstNameField.becomeFirstResponder()
	}

	func onFieldFocused(field: UITextField) {
//		scrollView.scrollRectToVisible(field.frame, animated: true)
	}

	@IBAction func saveAction(sender: AnyObject) {
		self.dismissViewControllerAnimated(true, completion: nil)
	}

}
