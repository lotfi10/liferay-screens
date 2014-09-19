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
import Foundation


public class DDLRecord: NSObject {

	public var fields: [DDLField] = []

	public var recordId: Int?

	public subscript(fieldName: String) -> DDLField? {
		return fieldBy(name: fieldName)
	}


	//MARK: Init
	
	public init(xml: String, locale: NSLocale) {
		super.init()

		let parser = DDLParser(locale: locale)

		parser.xml = xml

		if let parsedFields = parser.parse() {
		 	if !parsedFields.isEmpty {
				fields = parsedFields
			}
		}
	}

	public init(values: [String:AnyObject]) {
		super.init()

		updateCurrentValues(values)
	}


	//MARK: Public methods

	public func fieldBy(#name: String) -> DDLField? {
		for field in fields {
			if field.name == name {
				return field
			}
		}

		return nil
	}

	public func updateCurrentValues(values: [String:AnyObject]) {
		for (index,field) in enumerate(fields) {
			let fieldValue = (values[field.name] ?? nil) as? String
			if let fieldStringValue = fieldValue {
				field.currentStringValue = fieldStringValue

				fields.append(field)
			}
		}
	}


}