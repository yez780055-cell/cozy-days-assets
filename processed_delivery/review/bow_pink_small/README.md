# bow_pink_small: needs_review

小蝴蝶结原本与樱桃梗相连。已按轮廓排除果实与大部分梗，但中心连接处和尾带交叠区缺乏独立边界，需要人工确认，不应直接上线。

- `source_crop.png`：原图 ROI，未修改 RGB/Alpha，保持原始像素分辨率。
- `alpha_candidate.png`：与原裁片严格对齐的候选 Alpha。
- `trimap.png`：白=候选前景，黑=候选背景，灰=重点复核边缘；不是自动验收结果。
- 完整坐标见 `reports/crop_geometry.json`；正式候选 PNG 仍在 inventory 指定路径。
- 人工修复后必须重新检查白、深灰、粉色底及相邻元素污染，再修改状态。
