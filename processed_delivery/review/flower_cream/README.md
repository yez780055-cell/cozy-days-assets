# flower_cream: needs_review

花心原图带灰格与奶油色混合，中心负空间的真实边界不确定；现保留彩色花心、移除中性灰区域，需人工确认设计意图。

- `source_crop.png`：原图 ROI，未修改 RGB/Alpha，保持原始像素分辨率。
- `alpha_candidate.png`：与原裁片严格对齐的候选 Alpha。
- `trimap.png`：白=候选前景，黑=候选背景，灰=重点复核边缘；不是自动验收结果。
- 完整坐标见 `reports/crop_geometry.json`；正式候选 PNG 仍在 inventory 指定路径。
- 人工修复后必须重新检查白、深灰、粉色底及相邻元素污染，再修改状态。
