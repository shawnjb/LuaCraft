--- @meta
--- @class Vec3
--- Represents a 3D vector with x, y, z coordinates, providing methods for common vector operations.
Vec3 = {
    --- @type number
    x = 0,
    --- @type number
    y = 0,
    --- @type number
    z = 0,

    --- Creates a new Vec3 object.
    --- @param x number The x-coordinate.
    --- @param y number The y-coordinate.
    --- @param z number The z-coordinate.
    --- @return Vec3 @A new vector object.
    --- ```lua
    --- local vec = Vec3:new(1, 2, 3)
    --- ```
    new = function(x, y, z) end,

	--- Adds another vector to this vector.
	--- @param vec3 Vec3 The other vector to add.
	--- @return Vec3 @A new vector resulting from the addition.
	--- ```lua
	--- local vec1 = Vec3.new(1, 2, 3)
	--- local vec2 = Vec3.new(4, 5, 6)
	--- local result = vec1:add(vec2)
	--- print(result.x, result.y, result.z)  -- 5, 7, 9
	--- ```
	add = function(self, vec3) end,

	--- Subtracts another vector from this vector.
	--- @param vec3 Vec3 The other vector to subtract.
	--- @return Vec3 @A new vector resulting from the subtraction.
	--- ```lua
	--- local result = vec1:subtract(vec2)
	--- print(result.x, result.y, result.z)  -- -3, -3, -3
	--- ```
	subtract = function(self, vec3) end,

	--- Multiplies this vector by a scalar value.
	--- @param scalar number The scalar to multiply the vector by.
	--- @return Vec3 @A new vector resulting from the multiplication.
	--- ```lua
	--- local result = vec1:multiply(2)
	--- print(result.x, result.y, result.z)  -- 2, 4, 6
	--- ```
	multiply = function(self, scalar) end,

	--- Calculates the dot product of this vector and another vector.
	--- @param vec3 Vec3 The other vector.
	--- @return number @The dot product of the two vectors.
	--- ```lua
	--- local dot = vec1:dot(vec2)
	--- print(dot)  -- 32
	--- ```
	dot = function(self, vec3) end,

	--- Calculates the cross product of this vector and another vector.
	--- @param vec3 Vec3 The other vector.
	--- @return Vec3 @A new vector resulting from the cross product.
	--- ```lua
	--- local result = vec1:cross(vec2)
	--- print(result.x, result.y, result.z)  -- -3, 6, -3
	--- ```
	cross = function(self, vec3) end,

	--- Normalizes this vector (returns a unit vector in the same direction).
	--- @return Vec3 @A new normalized vector.
	--- ```lua
	--- local normalized = vec1:normalize()
	--- print(normalized.x, normalized.y, normalized.z)
	--- ```
	normalize = function(self) end,

	--- Calculates the magnitude (length) of this vector.
	--- @return number @The magnitude of the vector.
	--- ```lua
	--- local mag = vec1:magnitude()
	--- print(mag)  -- 3.74...
	--- ```
	magnitude = function(self) end,

	--- Calculates the distance between this vector and another vector.
	--- @param vec3 Vec3 The other vector.
	--- @return number @The distance between the two vectors.
	--- ```lua
	--- local distance = vec1:distance(vec2)
	--- print(distance)  -- 5.19...
	--- ```
	distance = function(self, vec3) end,

	--- Performs linear interpolation between this vector and another vector.
	--- @param vec3 Vec3 The target vector.
	--- @param t number The interpolation factor (between 0 and 1).
	--- @return Vec3 @A new vector resulting from the interpolation.
	--- ```lua
	--- local result = vec1:lerp(vec2, 0.5)
	--- print(result.x, result.y, result.z)  -- 2.5, 3.5, 4.5
	--- ```
	lerp = function(self, vec3, t) end,
}

--- Creates a new vector.
--- @param x number The x-coordinate.
--- @param y number The y-coordinate.
--- @param z number The z-coordinate.
--- @return Vec3 @A new Vec3 instance.
--- ```lua
--- local vec = Vec3.new(1, 2, 3)
--- ```
function Vec3.new(x, y, z) end