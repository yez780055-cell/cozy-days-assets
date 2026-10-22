# task_laptop: needs_review

外壳、键盘及屏幕内描边含较大中性灰区域，无法可靠区分原有蜡笔灰与烘焙棋盘残留。保留白色机身，未进行破坏性整体去灰；需要精修内描边。

- `source_crop.png`：原图 ROI，未修改 RGB/Alpha，保持原始像素分辨率。
- `alpha_candidate.png`：与原裁片严格对齐的候选 Alpha。
- `trimap.png`：白=候选前景，黑=候选背景，灰=重点复核边缘；不是自动验收结果。
- 完整坐标见 `reports/crop_geometry.json`；正式候选 PNG 仍在 inventory 指定路径。
- 人工修复后必须重新检查白、深灰、粉色底及相邻元素污染，再修改状态。
