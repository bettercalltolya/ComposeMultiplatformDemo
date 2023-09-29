import UIKit
import SwiftUI
import shared

struct ComposeViewController: UIViewControllerRepresentable {
    private let lifecycle: LifecycleRegistry
    private let topSafeArea: Float
    private let bottomSafeArea: Float

    init(lifecycle: LifecycleRegistry, topSafeArea: Float, bottomSafeArea: Float) {
        self.lifecycle = lifecycle
        self.topSafeArea = topSafeArea
        self.bottomSafeArea = bottomSafeArea
    }

    func makeUIViewController(context: Context) -> UIViewController {
        return Main_iosKt.MainViewController(
            lifecycle: lifecycle,
            topSafeArea: topSafeArea,
            bottomSafeArea: bottomSafeArea
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
