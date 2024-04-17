import { ChangeEventHandler, MouseEventHandler, useState } from 'react'
import './search.css'
import { Loader2 } from 'lucide-react'
import { X } from 'lucide-react'
import DropdownSearchItem from './DropdownSearchItem'
import useDebounceCustom from '@/hooks/useDebounceCustom'
import TooltipComponent from '../TooltipComponent'

function SearchBar() {
  const [showSearch, setShowSearch] = useState<boolean>(false)

  const [
    results,
    isSearching,
    searchTerm,
    setSearchTerm,
    setResults,
    setIsSearching
  ] = useDebounceCustom()

  const handleClick: MouseEventHandler<HTMLDivElement> = (
    event: React.MouseEvent<HTMLDivElement>
  ): void => {
    const target = event.target as HTMLDivElement
    if (
      target.classList.contains('search__button') ||
      target.closest('.search__button')
    ) {
      setShowSearch(!showSearch)
      if (showSearch) {
        setResults([])
        setSearchTerm('')
      }
    }
  }
  const handleCloseSearch = () => {
    setShowSearch(false)
    setSearchTerm('')
  }
  const overlay =
    // eslint-disable-next-line quotes
    "after:content-[''] after:absolute after:top-[-15%] after:left-[-75vw] after:z-[10] after:opacity-60 after:bg-black after:w-[200vw] after:h-screen"

  const handleChange: ChangeEventHandler<HTMLInputElement> = (e): void => {
    const target = e.target as HTMLInputElement
    setSearchTerm(target.value)
    setIsSearching(true)
  }

  return (
    <div
      className={`absolute top-[-23px] bg-background-secondary w-full ${showSearch ? 'show-search-bg ' + overlay : ''} `}
    >
      <div
        onClick={handleClick}
        className={`search z-50 ${showSearch ? 'show-search bg-white' : ''}`}
        id="search-bar"
      >
        <input
          type="text"
          placeholder="Gõ cái gì đó..."
          name="q"
          autoComplete="off"
          value={searchTerm}
          onChange={handleChange}
          className="search__input text-2xl bg-transparent text-background-main "
        />
        <div className="absolute right-24 top-7 ">
          {isSearching ? (
            <Loader2 size={15} className="animate-spin text-background-main" />
          ) : (
            <X
              className={`hover:cursor-pointer text-background-main ${showSearch ? 'block' : 'hidden'}`}
              size={15}
            />
          )}
        </div>
        <TooltipComponent tooltip={'Search'}>
          <div
            className="search__button bg-primary-movieColor"
            id="search-button"
          >
            <i className="ri-search-2-line search__icon"></i>
            <i className="ri-close-line search__close"></i>
          </div>
        </TooltipComponent>
        {searchTerm !== '' && (
          <DropdownSearchItem
            results={results}
            isSearching={isSearching}
            searchTerm={searchTerm}
            handleCloseSearch={handleCloseSearch}
          />
        )}
      </div>
    </div>
  )
}

export default SearchBar
