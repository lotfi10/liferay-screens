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


public class DDLFieldTableCell: UITableViewCell {

	public var tableView:UITableView?
	public var indexPath:NSIndexPath?
	public var formView:DDLFormTableView?

	public var field:DDLField? {
		didSet {
			field?.onPostValidation = onPostValidation
			onChangedField()
		}
	}

	public var isLastCell:Bool {
		var result = false

		if let indexPathValue = indexPath {
			if let rowCount = tableView?.numberOfRowsInSection(indexPathValue.section) {
				if formView!.showSubmitButton {
					result = (indexPathValue.row == rowCount - 2)
				}
				else {
					result = (indexPathValue.row == rowCount - 1)
				}
			}
		}

		return result
	}


	//MARK: UITableViewCell

	override public func awakeFromNib() {
		let simpleTapRecognizer = UITapGestureRecognizer(target: self, action: "simpleTapDetected")
		addGestureRecognizer(simpleTapRecognizer)
	}


	//MARK: Internal methods

	internal func onChangedField() {
	}

	internal func onPostValidation(valid: Bool) {
	}

	internal func changeCellHeight(height:CGFloat) {
		field?.currentHeight = height
		
		//FIXME Hack to fire the repaint of the cells
		tableView!.beginUpdates()
		tableView!.endUpdates()
	}

	internal func nextCell(indexPath:NSIndexPath) -> UITableViewCell? {
		var result:UITableViewCell?

		var row = indexPath.row
		let section = indexPath.section
		let rowCount = tableView?.numberOfRowsInSection(section)

		while ++row < rowCount {
			let currentPath = NSIndexPath(forRow: row, inSection: section)
			if let rowCell = tableView?.cellForRowAtIndexPath(currentPath) {
				if rowCell.canBecomeFirstResponder() {
					result = rowCell
					break
				}
			}
		}

		return result
	}

	internal func nextCellResponder(currentView:UIView) -> Bool {
		var result = false

		if let currentTextInput = currentView as? UITextInput {

			switch currentTextInput.returnKeyType! {

				case .Next:
					if let nextCell = nextCell(indexPath!) {
						if currentView.canResignFirstResponder() {
							currentView.resignFirstResponder()

							if nextCell.canBecomeFirstResponder() {
								result = nextCell.becomeFirstResponder()
							}

						}
					}

				case .Send:
					formView?.userActionWithName("submit-form")
					result = true

				default: ()
			}
		}
		
		return result
	}

	internal func changeDocumentUploadStatus(field: DDLFieldDocument) {
	}

	internal func simpleTapDetected() {
		formView!.resignFirstResponder()
	}

	internal func moveSubviewsVertically(offsetY:CGFloat) {
		for subview in contentView.subviews as [UIView] {
			if offsetY == 0.0 {
				subview.transform = CGAffineTransformIdentity
			}
			else {
				subview.transform = CGAffineTransformTranslate(
					CGAffineTransformIdentity, 0.0, offsetY)
			}
		}
	}

}