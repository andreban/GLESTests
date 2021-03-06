/*
 * Copyright 2019 André Cipriani Bandarra
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.mobplug.games.framework;

import com.mobplug.games.framework.interfaces.Animator;

public class StaticAnimator<T> implements Animator<T>{
	private static final long serialVersionUID = 4623043415695910922L;
	private T image;
	@Override
	public void addImage(T image, long delay) {
		this.image = image;
		
	}

	@Override
	public T getImage() {
		return this.image;
	}

	@Override
	public void start(long gameTime, boolean loop) {}

	@Override
	public void start(long gameTime) {}

	@Override
	public void update(long gameTime) {}

}
