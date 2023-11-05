package com.assignment.rdash.data

data class DesignLayouts(
    val success: Boolean,
    val data: ArrayList<DesignLayout>
)

data class DesignLayout(
    val id: String,
    val name: String,
    val type: String,
    val section: String,
    val tags: ArrayList<Tag>,
    val file: String,
    val version: Int,
    val uploaded_at: String,
    val uploaded_by: UploadDetails,
    val file_size: Double
)

data class Tag(
    val id: String,
    val name: String
)

data class UploadDetails(
    val id: String,
    val name: String,
    val photo: String,
    val organization_name: String
)