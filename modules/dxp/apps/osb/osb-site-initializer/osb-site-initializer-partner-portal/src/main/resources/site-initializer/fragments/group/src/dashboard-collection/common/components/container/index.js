/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import classNames from 'classnames';
import React from 'react';

function Container({
	children,
	className,
	footer,
	positionButton = false,
	title,
}) {
	return (
		<div
			className={`bg-neutral-0 d-flex flex-column justify-content-between py-4 rounded shadow-sm ${className}`}
		>
			<div>
				<div className="font-weight-semi-bold h5 m-0">{title}</div>

				<div>
					<hr className="mb-4 mt-1" />
				</div>
			</div>

			<div className="align-items-stretch d-flex flex-column">
				{children}

				{footer && (
					<div
						className={classNames('', {
							'd-flex': positionButton,
						})}
					>
						<hr className="mb-3 mt-1" />

						<div
							className={classNames('d-flex', {
								'': positionButton,
							})}
						>
							{footer}
						</div>
					</div>
				)}
			</div>
		</div>
	);
}

export default Container;
