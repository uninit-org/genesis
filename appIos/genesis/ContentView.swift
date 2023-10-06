//
//  ContentView.swift
//  genesis
//
//  Created by Luna on 10/6/23.
//  Copyright © 2023 genesis. All rights reserved.
//

import UIKit
import SwiftUI
import genesisCore

struct ComposeView: UIViewControllerRepresentable {
  func makeUIViewController(context: Context) -> UIViewController {
    Main_iosKt.MainViewController()
  }

  func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
  var body: some View {
    ComposeView().ignoresSafeArea(.keyboard) // Compose has own keyboard handler
  }
}
