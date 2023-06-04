import SwiftUI

@main
struct iOSApp: App {
    private var lifecycleHolder: LifecycleHolder { LifecycleHolder() }
    
	var body: some Scene {
		WindowGroup {
            GeometryReader { geo in
                ComposeViewController(
                    lifecycle: lifecycleHolder.lifecycle,
                    topSafeArea: Float(geo.safeAreaInsets.top),
                    bottomSafeArea: Float(geo.safeAreaInsets.bottom)
                )
                .edgesIgnoringSafeArea(.all)
                .onTapGesture {
                    UIApplication.shared.sendAction(
                        #selector(UIResponder.resignFirstResponder),
                        to: nil, from: nil, for: nil
                    )
                }
            }
		}
	}
}
