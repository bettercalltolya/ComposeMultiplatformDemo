//
//  LifecycleHolder.swift
//  iosApp
//
//  Created by Anatolijs Petjko on 04/06/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

class LifecycleHolder : ObservableObject {
    let lifecycle: LifecycleRegistry
    
    init() {
        lifecycle = LifecycleRegistryKt.LifecycleRegistry()
        
        lifecycle.onCreate()
    }
    
    deinit {
        lifecycle.onDestroy()
    }
}
