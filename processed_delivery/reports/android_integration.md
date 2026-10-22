# Android 集成说明

## 当前工程检查结果

当前任务开始时工作目录为空。仓库 `yez780055-cell/cozy-days-assets` 只有五个素材 ZIP，没有 Gradle、AndroidManifest、Compose、Flutter 或 React 项目代码，因此无法确认「小饱日常」的实际技术栈，也没有可修改的真实业务页面。

本机相邻的另一个工程是 `life-space-foundation`，使用 React 19 + Vite + Capacitor 7，其包名为 `com.lifespace.foundation.test`、应用名 `Life Space Test`。没有证据证明它是本 App，未向其写入资源。

本轮已完成**资源暂存与可移植引用示例**，没有完成真实 App 集成。Android SDK、Java 编译器未发现，因此没有运行 aapt、Gradle 或安卓设备测试。需要提供本 App 源码目录后才能确认使用哪一种接入方式。

## 已备好的原生 Android 资源

- `../android/res/drawable-nodpi/`：35 个通过本轮 QA 的 PNG，保持原始像素分辨率；nodpi 避免 Android 按资源密度预缩放。
- `../android/res/drawable/`：9 个与 SVG 路径、颜色、透明度相对应的 VectorDrawable，以及 `paper_repeat.xml`。
- `../android/res/layout/cozy_days_asset_preview.xml`：使用卧室、顶部猫、空状态猫与空心爱心的独立预览布局。
- `../android/java/com/cozydays/assets/FocalCropImageView.java`：无第三方依赖的等比 cover 和焦点裁切控件。
- `../android/assets/cozy_days/asset_manifest.json`：仅列合格资源的元数据。

`needs_review` 的 9 个候选不在 `android/res/` 中。不要直接将整个 `raster/` 文件夹当作已合格资源导入。

如果实际工程采用 XML：将上述 res 子目录合并到 `app/src/main/res/`；将 Java 类放入 `app/src/main/java/com/cozydays/assets/`，或调整类包名和布局中的全限定名称。然后在预览 Activity 中使用 `setContentView(R.layout.cozy_days_asset_preview)`。示例布局的猫咪宽度是 280dp，真实页面应按可用宽度约 68% 设置，而非把像素宽直接当作 dp。

XML 可通过 `android:src="@drawable/cats_header"`、`android:src="@drawable/heart_pink_outline"` 引用。VectorDrawable 的默认尺寸为 32dp，实际 View 可设为 24/48/96dp。原生 VectorDrawable 需要 API 21+；更低版本需要工程已有的兼容加载组件。

如果实际工程采用 Compose，PNG/VectorDrawable 均可用 `painterResource(R.drawable.cats_header)` 等方式读取。角色用 `ContentScale.Fit`，`Modifier.width(...)` 指定 dp；焦点 cover 可通过 `AndroidView` 包装提供的控件，或用等价 Canvas 矩阵。不要直接用 `FillBounds`。

如果实际工程采用 Flutter，保留 `raster/` 和 `vectors/` 的分组结构复制到项目 `assets/cozy_days/` 并登记 pubspec；PNG 用 `Image.asset(..., fit: BoxFit.contain)`，SVG 需要项目已支持的 SVG 加载库。原生 res 暂存目录不是 Flutter 的资产登记方式。

如果实际工程采用 React/Capacitor，将 inventory 中 `ready` 项的 PNG/SVG 复制到 `public/assets/cozy_days/`，通过真实 URL 加载，随后按项目已有构建流程构建并同步 Android。不要手工修改 Capacitor 生成的 `android/app/src/main/assets/public/` 文件。

## 建议尺寸与缩放

| 类别 | 建议显示尺寸 | 规则 |
|---|---|---|
| 卧室背景 | 宽度填满；优先高=宽×941/1672 | 等比；需要固定高度时用下述焦点 cover |
| 顶部猫 | 头图宽度约 62%–72% | fit；右下锚定；保留透明安全边 |
| 空状态猫 | 宽 220–280dp | fit；与顶部猫分别引用 |
| 边角猫/文具 | 宽 56–140dp | fit；根据页面留白调整 |
| 任务插画 | 48–72dp | fit；电脑/扫帚暂未合格，不应上线 |
| 胶带/蝴蝶结/花 | 24–100dp | fit；不要强制把长胶带塞成正方形 |
| 星/心/水滴 | 24/32/48dp，或按用途放大 | 优先 VectorDrawable/SVG；不设固定物理像素描边 |

所有透明位图的正式文件都保留来源分辨率；列表显示时缩小不意味着要重新导出低分辨率文件。避免加载 contact sheet 或三色底预览作为实际素材。

## 卧室焦点 cover

源图为 1672×941。焦点取 `(fx,fy)=(0.62,0.45)`。对于内容区 `(W,H)`：

```text
s = max(W / 1672, H / 941)
dx = clamp(W/2 - fx*1672*s, W-1672*s, 0)
dy = clamp(H/2 - fy*941*s, H-941*s, 0)
```

平移后等比绘制。提供的 Java 控件与 `header_responsive.png` 使用相同几何规则。已经生成 320、360、393、412、480、600dp 六种宽度的预览；实际 Android 字体、系统栏和设备裁切仍未验证。

网页若使用 CSS `object-position:62% 45%`，其语义是溢出区域的比例分配，并不严格等于上述“把源图焦点放到视口中心”。若需精确一致，请采用附带浏览器预览中的焦点矩阵实现。

顶部猫本身带粉色被褥与紫枕，叠在背景床铺上时只叠这一层，不要另叠其他床铺 PNG。预览中的摆位经过目检；必要时选择仅背景方案，或在设计阶段为背景床铺另做人工遮挡层，不能拉伸猫来拼接。

## 纸纹

`paper_texture.png` 是 1254×1254 的无缝候选成品，已通过本轮平铺 QA。`paper_repeat.xml` 是原生重复 bitmap 示例，nodpi 下按实际解码像素平铺；如果产品需要固定 dp 的纹理重复周期，请通过 BitmapShader 的 local matrix 设置约 240–360dp 的 tile，而不是修改或降低源图分辨率。

不要对整张纸纹做全屏非等比拉伸；这会改变颗粒的形状。Web 预览使用 `background-repeat:repeat`，并按等比 tile 尺寸绘制。

## 交付核对

`asset_inventory.csv` 为唯一状态依据，`manifest_coverage.csv` 可按原交接 44 项逐行核对。原生 staging 包含 35 个 ready PNG + 9 个 VectorDrawable，未包含待修复项。真实工程接入后必须补做构建、实际页面和至少窄/宽两档安卓设备验收；本轮的浏览器/静态预览不替代这些检查。
