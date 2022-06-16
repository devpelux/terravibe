/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
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
 */

//Source: https://github.com/FabricMC/fabric/pull/1085

package xyz.devpelux.terravibe.core.fabric.api.extensions;

import net.minecraft.entity.damage.DamageSource;

/**
 * Allows creating custom Damage Sources without subclassing it.
 */
public class FabricDamageSource extends DamageSource {
	public FabricDamageSource(String name) {
		super(name);
	}

	@Override
	public FabricDamageSource setFallingBlock() {
		super.setFallingBlock();
		return this;
	}

	@Override
	public FabricDamageSource setBypassesArmor() {
		super.setBypassesArmor();
		return this;
	}

	@Override
	public FabricDamageSource setBypassesProtection() {
		super.setBypassesProtection();
		return this;
	}

	@Override
	public FabricDamageSource setOutOfWorld() {
		super.setOutOfWorld();
		return this;
	}

	@Override
	public FabricDamageSource setUnblockable() {
		super.setUnblockable();
		return this;
	}

	@Override
	public FabricDamageSource setFire() {
		super.setFire();
		return this;
	}
}
