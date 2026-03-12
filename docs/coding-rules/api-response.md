# API Response Convention

## Goal
Provide a consistent, predictable JSON structure for all backoffice APIs so the frontend can handle success/failure in a uniform way.

## Standard JSON Structure
All API responses should follow the common envelope format below:

```json
{
  "success": true,
  "code": "SUCCESS",
  "message": null,
  "data": {
    "fileSetId": 123
  }
}
```

### Fields
- `success`: `true` on success, `false` on error.
- `code`: business or system code (ex: `SUCCESS`, `VALIDATION_ERROR`, `NOT_FOUND`).
- `message`: human-readable message, `null` on success.
- `data`: payload. `null` when no payload is needed.

## Examples

### Success with data
```json
{
  "success": true,
  "code": "SUCCESS",
  "message": null,
  "data": {
    "fileSetId": 123
  }
}
```

### Success without data
```json
{
  "success": true,
  "code": "SUCCESS",
  "message": null,
  "data": null
}
```

### Failure
```json
{
  "success": false,
  "code": "SOME_ERROR",
  "message": "Error message",
  "data": null
}
```

## Usage Notes
- Prefer the common envelope for all endpoints to keep response handling consistent.
- When returning lists, place the array inside `data`.
- Do not return raw primitives (ex: `123`) directly.
- For legacy endpoints returning raw values, migrate them to the envelope format.

## i18n Handling
- Use `code` as the i18n message key.
- `message` is a fallback string (default language) for cases where the client cannot resolve the key.
- If parameters are needed for interpolation, add a dedicated `messageParams` field in the response contract.
